package com.rabbit.dayfilm.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        CodeSet code = CodeSet.findCodeByMsg(authException.getMessage());
        if (code == null) code = CodeSet.FAIL_AUTHORIZATION;

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(code)));
    }
}
