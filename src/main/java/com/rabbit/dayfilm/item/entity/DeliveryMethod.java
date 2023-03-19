package com.rabbit.dayfilm.item.entity;

import lombok.Getter;

@Getter
public enum DeliveryMethod {
    PARCEL("택배수령"),
    VISIT("방문수령");
    private final String value;

    DeliveryMethod(String value) {
        this.value = value;
    }

}
