package com.rabbit.dayfilm.auth.filter;

import com.rabbit.dayfilm.auth.AuthUtil;
import com.rabbit.dayfilm.auth.dto.LoginInfo;
import com.rabbit.dayfilm.auth.service.AuthService;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicAuthenticationFilter {
    private final AuthService authService;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private static final String BEARER = "Bearer ";

    public JWTFilter(AuthenticationManager authenticationManager,
                     AuthService authService,
                     AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager);
        this.authService = authService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (accessToken != null) accessToken = accessToken.substring(BEARER.length());
        else {
            onUnsuccessfulAuthentication(request, response, new FilterException(CodeSet.TOKEN_EMPTY));
            return;
        }

        try {
            if (AuthUtil.accessVerify(accessToken)) {
                LoginInfo userClaim = authService.getLoginInfoByToken(accessToken);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userClaim.getEmail(), userClaim.getPw()
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                chain.doFilter(request, response);
            } else onUnsuccessfulAuthentication(request, response, new FilterException(CodeSet.ACCESS_TOKEN_EXPIRED));
        } catch (FilterException e) {
            onUnsuccessfulAuthentication(request, response, new FilterException(e.getCode()));
        }
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
        try {
            authenticationEntryPoint.commence(request, response, failed);
        } catch (ServletException e) {
            throw new FilterException(CodeSet.INTERNAL_SERVER_ERROR);
        }
    }
}