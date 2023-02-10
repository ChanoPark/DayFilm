package com.rabbit.dayfilm.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.dayfilm.auth.*;
import com.rabbit.dayfilm.auth.dto.LoginInfo;
import com.rabbit.dayfilm.auth.dto.SignReqDto;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.auth.service.AuthService;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class SignStoreFilter extends UsernamePasswordAuthenticationFilter {

    public SignStoreFilter(AuthenticationManager authenticationManager,
                           AuthenticationEntryPoint authenticationEntryPoint,
                           AuthService authService,
                           StoreRepository storeRepository,
                           AuthRedisRepository authRedisRepository,
                           PasswordEncoder passwordEncoder) {
        super(authenticationManager);
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authService = authService;
        this.storeRepository = storeRepository;
        this.authRedisRepository = authRedisRepository;
        this.passwordEncoder = passwordEncoder;
        setFilterProcessesUrl("/sign/store");
    }

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthService authService;
    private final AuthRedisRepository authRedisRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BEARER = "Bearer ";

    private LoginInfo claim;
    private String refreshToken;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        SignReqDto.SignStore signStoreDto;

        try {
            signStoreDto = objectMapper.readValue(request.getInputStream(), SignReqDto.SignStore.class);
        } catch (IOException e) {
            throw new FilterException(CodeSet.INTERNAL_SERVER_ERROR);
        }
        claim = new LoginInfo(signStoreDto.getEmail(), signStoreDto.getPw());

        String originPw = signStoreDto.getPw();
        String encodedPw = passwordEncoder.encode(signStoreDto.getPw());
        signStoreDto.changeEncodedPw(encodedPw);

        refreshToken = AuthUtil.createRefreshToken(claim.getEmail());
        authService.signStore(signStoreDto, refreshToken);

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
        if (claim != null && storeRepository.findStoreByEmail(claim.getEmail()).isPresent()) {
            storeRepository.deleteStoreByEmail(claim.getEmail());
            authRedisRepository.deleteById(claim.getEmail());
        }

        SecurityContextHolder.clearContext();
        authenticationEntryPoint.commence(request, response, failed);
    }
}
