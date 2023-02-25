package com.rabbit.dayfilm.basket.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.rabbit.dayfilm.item.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BasketInfo {
    private Product product;
    private LocalDateTime started;
    private LocalDateTime ended;
    private String title;
    private Integer pricePerOne;
    private Integer pricePerFive;
    private Integer pricePerTen;

    @QueryProjection
    public BasketInfo(Product product, LocalDateTime started, LocalDateTime ended, String title,
                      Integer pricePerOne, Integer pricePerFive, Integer pricePerTen) {
        this.product = product;
        this.started = started;
        this.ended = ended;
        this.title = title;
        this.pricePerOne = pricePerOne;
        this.pricePerFive = pricePerFive;
        this.pricePerTen = pricePerTen;
    }
}
