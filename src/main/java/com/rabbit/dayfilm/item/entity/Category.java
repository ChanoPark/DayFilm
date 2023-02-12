package com.rabbit.dayfilm.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    CAMERA("카메라"),
    LENS("렌즈"),
    CAMCORDER( "캠코더"),
    DRONE("드론, 액션캠"),
    MIC( "음향 및 마이크"),
    LIGHT( "조명"),
    ACCESSORY( "액세서리"),
    ETC("기타");


    private final String value;

    Category(String value) {
        this.value = value;
    }


    @JsonCreator
    public static Category get(String value) {
        for (Category c : Category.values()) {
            if(c.getValue().equals(value)) {
                return c;
            }
        }
        return null;
    }
}
