package com.rabbit.dayfilm.basket.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BasketReqDto {

    @Getter
    @Setter
    public static class DeleteBaskets {
        private List<Long> basketIds;
    }
}