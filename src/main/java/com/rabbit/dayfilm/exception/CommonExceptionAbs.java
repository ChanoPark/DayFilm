package com.rabbit.dayfilm.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommonExceptionAbs extends RuntimeException implements CommonException  {
    private static final int STACK_TRACE_LINE_LIMIT = 3;
    @Override
    public List<String> fewStacktrace() {
        return Arrays.stream(Arrays.copyOfRange(getStackTrace(), 0, STACK_TRACE_LINE_LIMIT)).map(item -> item.toString()).collect(Collectors.toList());
    }
}
