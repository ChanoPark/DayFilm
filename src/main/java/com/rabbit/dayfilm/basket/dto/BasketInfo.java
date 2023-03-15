package com.rabbit.dayfilm.basket.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.store.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BasketInfo {
    private Long basketId;
    private Product product;
    private LocalDateTime started;
    private LocalDateTime ended;
    private String title;
    private Integer pricePerOne;
    private Integer pricePerFive;
    private Integer pricePerTen;
    private DeliveryMethod method;
    private Address address;

    @QueryProjection
    public BasketInfo(Long basketId, Product product, LocalDateTime started, LocalDateTime ended, String title,
                      Integer pricePerOne, Integer pricePerFive, Integer pricePerTen, DeliveryMethod method, Address address) {
        this.basketId = basketId;
        this.product = product;
        this.started = started;
        this.ended = ended;
        this.title = title;
        this.pricePerOne = pricePerOne;
        this.pricePerFive = pricePerFive;
        this.pricePerTen = pricePerTen;
        this.method = method;
        this.address = address;
    }
}
