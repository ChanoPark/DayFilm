package com.rabbit.dayfilm.common.response;

import com.rabbit.dayfilm.common.CodeSet;

import java.util.List;

public class ErrorResponse extends ResponseAbs {

    transient String cause;
    transient List<String> stackTrace;

    public ErrorResponse(CodeSet code, String cause, List<String> stackTrace) {
        super(code);
        super.setDetail(cause);
        super.setValue(stackTrace);
        this.cause = cause;
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(CodeSet code, String detail, String cause) {
        super(code);
        super.setDetail(detail);
        this.cause = cause;
    }

    public ErrorResponse(CodeSet code, String detail) {
        super(code);
        super.setDetail(detail);
    }

    public ErrorResponse(CodeSet code) {
        super(code);
    }
}