package com.rabbit.dayfilm.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rabbit.dayfilm.common.Constant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category implements Constant {
    CAMERA("카메라"),
    LENS("렌즈"),
    CAMCORDER( "캠코더"),
    DRONE("드론, 액션캠"),
    MIC( "음향 및 마이크"),
    LIGHT( "조명"),
    ACCESSORY( "액세서리"),
    ETC("기타");


    private final String value;

    @JsonCreator
    public static Category from(@JsonProperty("value") String value) {
        for (Category category : Category.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}
