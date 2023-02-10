package com.rabbit.dayfilm.exception;

import com.rabbit.dayfilm.common.CodeSet;
import org.springframework.security.core.AuthenticationException;


public class FilterException extends AuthenticationException {
    public FilterException(CodeSet code) {
        super(code.getCode());
        this.code = code;
    }
    private CodeSet code;
}
