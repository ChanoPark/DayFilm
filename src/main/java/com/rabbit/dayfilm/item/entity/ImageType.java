package com.rabbit.dayfilm.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {
    PRODUCT("상품사진"),
    INFO("설명사진");

    private final String value;

}

