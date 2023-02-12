package com.rabbit.dayfilm.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum ItemStatus {
    GOOD( "상"),
    AVERAGE( "중"),
    BAD( "하");
    private final String value;

    ItemStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ItemStatus get(@JsonProperty("value") String value) {
        for (ItemStatus i : ItemStatus.values()) {
            if(i.getValue().equals(value)) {
                return i;
            }
        }
        return null;
    }
}
