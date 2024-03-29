package com.rabbit.dayfilm.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.dayfilm.auth.AuthUtil;
import com.rabbit.dayfilm.auth.RSAKey;
import com.rabbit.dayfilm.auth.Role;
import com.rabbit.dayfilm.auth.UserInfo;
import com.rabbit.dayfilm.auth.dto.AuthResDto;
import com.rabbit.dayfilm.auth.dto.LoginInfo;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter(AuthenticationManager authenticationManager,
                       AuthRedisRepository authRedisRepository,
                       AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager);
        this.authRedisRepository = authRedisRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
        setFilterProcessesUrl("/login");
    }

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthRedisRepository authRedisRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private LoginInfo claim;
    private static final String BEARER = "Bearer ";


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            claim = objectMapper.readValue(request.getInputStream(), LoginInfo.class);
        } catch (IOException e) {
            throw new FilterException(CodeSet.INTERNAL_SERVER_ERROR);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                claim.getEmail(), claim.getPw()
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

        String refreshToken = AuthUtil.createRefreshToken(claim.getEmail(), keys.getPrivateKey());
        String accessToken = AuthUtil.createAccessToken(claim.getEmail(), keys.getPrivateKey(), encryptedPw);

        UserInfo user = authRedisRepository.findById(claim.getEmail()).orElseThrow(() -> new FilterException(CodeSet.INVALID_USER));
        user.changeRefreshToken(refreshToken);
        authRedisRepository.save(user);

        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
        response.setHeader("Refresh-Token", BEARER + refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(new AuthResDto(user.getNickname(), user.getRole(), user.getPk())));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        authenticationEntryPoint.commence(request, response, failed);
    }
}
