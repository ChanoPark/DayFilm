package com.rabbit.dayfilm.config;

import com.rabbit.dayfilm.auth.filter.JWTFilter;
import com.rabbit.dayfilm.auth.filter.SignUserFilter;
import com.rabbit.dayfilm.auth.service.AuthService;
import com.rabbit.dayfilm.auth.service.AuthServiceImpl;
import com.rabbit.dayfilm.auth.filter.LoginFilter;
import com.rabbit.dayfilm.auth.filter.SignStoreFilter;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import com.rabbit.dayfilm.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthService authService;
    private final AuthRedisRepository authRedisRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), authenticationEntryPoint);
        SignStoreFilter signStoreFilter = new SignStoreFilter(authenticationManager(authenticationConfiguration), authenticationEntryPoint, authService, storeRepository, authRedisRepository,passwordEncoder);
        SignUserFilter signUserFilter = new SignUserFilter(authenticationManager(authenticationConfiguration), authenticationEntryPoint, authService, userRepository, authRedisRepository, passwordEncoder);
        JWTFilter jwtFilter = new JWTFilter(authenticationManager(authenticationConfiguration), authService, authenticationEntryPoint);

        return http
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests()
                .antMatchers("/yes").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(signStoreFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(signUserFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/doc", "/swagger*/**", "/favicon*/**", "/v2/api-docs");
    }
}