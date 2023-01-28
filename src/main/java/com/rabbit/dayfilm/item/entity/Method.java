package com.rabbit.dayfilm.item.entity;

public enum Method {
    PARCEL(1000, "택배수령"),
    VISIT(2000, "방문수령");
    private final int code;
    private final String value;

    Method(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
