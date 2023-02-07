package com.rabbit.dayfilm.exception;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private List<String> getFewStackTrace(StackTraceElement[] stackTrace) {
        return Arrays.stream(Arrays.copyOfRange(stackTrace, 0, 3)).map(item -> item.toString()).collect(Collectors.toList());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidPannelException(CustomException e) {
        log.error("CustomException: {}", e.getElement());
        final ErrorResponse response = new ErrorResponse(CodeSet.BAD_REQUEST, e.getElement(), getFewStackTrace(e.getStackTrace()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
