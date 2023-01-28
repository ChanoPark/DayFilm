package com.rabbit.dayfilm.item.entity;

import lombok.Getter;

@Getter
public enum ItemStatus {
    GOOD(3000, "상"),
    AVERAGE(2000, "중"),
    BAD(1000, "하"),;

    private final int code;
    private final String value;

    ItemStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
