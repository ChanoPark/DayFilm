package com.rabbit.dayfilm.item.entity;

public enum Category {
    CAMERA(1000, "카메라"),
    LENS(2000, "렌즈"),
    CAMCORDER(3000, "캠코더"),
    DRONE(4000, "드론, 액션캠"),
    MIC(5000, "음향 및 마이크"),
    LIGHT(6000, "조명"),
    ACCESSORY(7000, "액세서리"),
    ETC(8000, "기타");


    private final int code;
    private final String value;

    Category(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
