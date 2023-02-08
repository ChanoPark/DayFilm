package com.rabbit.dayfilm.common;

public enum CodeSet {
    OK("0000", "OK"),
    BAD_REQUEST("1000", "BAD_REQUEST");
    private String code;
    private String message;

    CodeSet(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
