package com.rabbit.dayfilm.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliveryCode {
    LOTTE("롯데 택배");

    /**
     * 포맷 미정 (택배사 API 결정 후 작성 예정)
     */

    private String title;

}
