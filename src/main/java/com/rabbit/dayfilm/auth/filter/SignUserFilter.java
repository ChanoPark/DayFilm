package com.rabbit.dayfilm.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.dayfilm.auth.AuthUtil;
import com.rabbit.dayfilm.auth.RSAKey;
import com.rabbit.dayfilm.auth.Role;
import com.rabbit.dayfilm.auth.dto.AuthResDto;
import com.rabbit.dayfilm.auth.dto.SignReqDto;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.auth.service.AuthService;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import com.rabbit.dayfilm.user.repository.UserRepository;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUserFilter extends UsernamePasswordAuthenticationFilter {
    public SignUserFilter(AuthenticationManager authenticationManager,
                          AuthenticationEntryPoint authenticationEntryPoint,
                          AuthService authService,
                          UserRepository userRepository,
                          AuthRedisRepository authRedisRepository,
                          PasswordEncoder passwordEncoder) {
        super(authenticationManager);
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authService = authService;
        this.userRepository = userRepository;
        this.authRedisRepository = authRedisRepository;
        this.passwordEncoder = passwordEncoder;
        setFilterProcessesUrl("/sign/user");
    }

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthService authService;
    private final AuthRedisRepository authRedisRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BEARER = "Bearer ";

    private SignReqDto.SignUser claim;
    private RSAKey keys;
    private String refreshToken;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            claim = objectMapper.readValue(request.getInputStream(), SignReqDto.SignUser.class);
        } catch (IOException e) {
            throw new FilterException(CodeSet.INTERNAL_SERVER_ERROR);
        }

        String originPw = claim.getPw();
        String encodedPw = passwordEncoder.encode(claim.getPw());
        claim.changeEncodedPw(encodedPw);

        keys = AuthUtil.generateKey();
        refreshToken = AuthUtil.createRefreshToken(claim.getEmail(), keys.getPrivateKey());
        authService.signUser(claim, refreshToken);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                claim.getEmail(), originPw
        );

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {
        String encryptedPw = AuthUtil.encrypt(claim.getPw(), keys.getPublicKey());

        String accessToken = AuthUtil.createAccessToken(claim.getEmail(), keys.getPrivateKey(), encryptedPw);

        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
        response.setHeader("Refresh-Token", BEARER + refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(new AuthResDto(claim.getNickname(), Role.USER)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        if (claim != null && userRepository.findUserByEmail(claim.getEmail()).isPresent()) {
            userRepository.deleteUserByEmail(claim.getEmail());
            authRedisRepository.deleteById(claim.getEmail());
        }

        SecurityContextHolder.clearContext();
        authenticationEntryPoint.commence(request, response, failed);
    }
}
