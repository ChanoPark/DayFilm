package com.rabbit.dayfilm.common;

public enum CodeSet {
    OK("0000", "OK"),
    BAD_REQUEST("1000", "BAD_REQUEST"),

    SIGNED_USER("2000", "이미 회원입니다."),
    INTERNAL_SERVER_ERROR("9000", "서버 동작 중 문제가 발생했습니다."),
    INVALID_ALGORITHM("9001", "알고리즘이 유효하지 않습니다."),
    INVALID_KEY("9002", "키 값이 유효하지 않습니다.");
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
