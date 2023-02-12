package com.rabbit.dayfilm.auth.filter;

import com.rabbit.dayfilm.auth.AuthUtil;
import com.rabbit.dayfilm.auth.RSAKey;
import com.rabbit.dayfilm.auth.UserInfo;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.auth.service.AuthService;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
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

public class JWTReissueFilter extends UsernamePasswordAuthenticationFilter {

    public JWTReissueFilter(AuthenticationManager authenticationManager,
                            AuthService authService,
                            AuthRedisRepository authRedisRepository,
                            AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager);
        this.authService = authService;
        this.authRedisRepository = authRedisRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
        setFilterProcessesUrl("/reissue");
    }

    private UserInfo claim;
    private final AuthRedisRepository authRedisRepository;
    private final AuthService authService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private static final String BEARER = "Bearer ";
    private static final String REFRESH_TOKEN = "Refresh-Token";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String refreshToken = request.getHeader(REFRESH_TOKEN);
        if (refreshToken == null) throw new FilterException(CodeSet.TOKEN_EMPTY);
        else refreshToken = refreshToken.substring(BEARER.length());

        if (AuthUtil.refreshVerify(refreshToken)) {
            claim = authRedisRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new FilterException(CodeSet.REFRESH_TOKEN_INVALID));
            String decryptPw = authService.decryptPwInToken(refreshToken, claim.getPw());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    claim.getEmail(), decryptPw
            );

            return getAuthenticationManager().authenticate(authenticationToken);
        } else throw new FilterException(CodeSet.REFRESH_TOKEN_INVALID);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {
        RSAKey keys = AuthUtil.generateKey();

        String accessToken = AuthUtil.createAccessToken(claim.getEmail(), keys.getPrivateKey(), claim.getPw());
        String refreshToken = AuthUtil.createRefreshToken(claim.getEmail(), keys.getPrivateKey());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
        response.setHeader(REFRESH_TOKEN, BEARER + refreshToken);

        UserInfo user = authRedisRepository.findById(claim.getEmail()).orElseThrow(() -> new FilterException(CodeSet.INVALID_USER));
        user.changeRefreshToken(refreshToken);
        authRedisRepository.save(user);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        authenticationEntryPoint.commence(request, response, failed);
    }
}
