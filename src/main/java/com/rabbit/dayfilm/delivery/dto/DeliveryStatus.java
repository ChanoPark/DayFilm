package com.rabbit.dayfilm.delivery.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.exception.CustomException;
import lombok.Getter;

@Getter
public enum DeliveryStatus {
    INFORMATION_RECEIVED,
    AT_PICKUP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED;

    @JsonCreator
    public static DeliveryStatus fromString(String status) {
        for (DeliveryStatus d : DeliveryStatus.values()) {
            if (d.name().toLowerCase().equals(status))
                return d;
        }
        throw new CustomException("배송 정보가 올바르지 않습니다.");
    }
}
