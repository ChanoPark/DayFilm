package com.rabbit.dayfilm.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.dayfilm.auth.AuthUtil;
import com.rabbit.dayfilm.auth.RSAKey;
import com.rabbit.dayfilm.auth.dto.LoginDto;
import com.rabbit.dayfilm.auth.dto.SignReqDto;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.auth.service.AuthService;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import com.rabbit.dayfilm.user.UserRepository;
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

    private LoginDto claim;
    private String refreshToken;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        SignReqDto.SignUser singUserDto;

        try {
            singUserDto = objectMapper.readValue(request.getInputStream(), SignReqDto.SignUser.class);
        } catch (IOException e) {
            throw new FilterException(CodeSet.INTERNAL_SERVER_ERROR);
        }
        claim = new LoginDto(singUserDto.getEmail(), singUserDto.getPw());

        String originPw = singUserDto.getPw();
        String encodedPw = passwordEncoder.encode(singUserDto.getPw());
        singUserDto.changeEncodedPw(encodedPw);

        refreshToken = AuthUtil.createRefreshToken(claim.getEmail());
        authService.signUser(singUserDto, refreshToken);

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
        RSAKey keys = AuthUtil.generateKey();
        String encryptedPw = AuthUtil.encrypt(claim.getPw(), keys.getPublicKey());

        String accessToken = AuthUtil.createAccessToken(claim.getEmail(), keys.getPrivateKey(), encryptedPw);

        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
        response.setHeader("Refresh-Token", BEARER + refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        /**
         * Response는 추후 프론트와 협의 후 추가 예정
         */
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
