package com.rabbit.dayfilm.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Method {
    PARCEL("택배수령"),
    VISIT("방문수령");
    private final String value;

    Method(String value) {
        this.value = value;
    }

}
