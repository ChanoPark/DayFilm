package com.rabbit.dayfilm.common;

public enum CodeSet {
    OK("0000", "OK"),
    BAD_REQUEST("1000", "BAD_REQUEST"),

    SIGNED_USER("2000", "이미 회원입니다."),
    INVALID_USER("2001", "회원 정보가 올바르지 않습니다."),
    TOKEN_EMPTY("2002", "토큰이 존재하지 않습니다."),
    ACCESS_TOKEN_EXPIRED("2003", "엑세스 토큰이 만료되었습니다."),
    ACCESS_TOKEN_INVALID("2004", "엑세스 토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_EXPIRED("2005", "리프레시 토큰이 만료되었습니다."),
    REFRESH_TOKEN_INVALID("2006", "리프레시 토큰이 유효하지 않습니다."),
    TOKEN_INVALID("2007", "토큰이 유효하지 않습니다."),
    FAIL_AUTHENTICATION("2008", "인증에 실패했습니다."),
    FAIL_AUTHORIZATION("2009", "권한이 없습니다"),
    NOT_FOUND_USER("2010", "회원이 존재하지 않습니다."),
    DUPLICATE_NICKNAME("3000", "중복된 닉네임입니다."),
    BATCH_ERROR("8000", "배치 서버 동작 중 문제가 생겼습니다."),
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

    public static CodeSet findCodeByMsg(String msg) {
        for (CodeSet codeSet : CodeSet.values()) {
            if (codeSet.getMessage().equals(msg)) return codeSet;
        }
        return null;
    }
}
