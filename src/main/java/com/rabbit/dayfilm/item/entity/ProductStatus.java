package com.rabbit.dayfilm.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    RENTAL("렌탈"),
    REPAIR("수리"),
    AVAILABLE("사용 가능");

    private final String value;

}
