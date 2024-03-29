package com.rabbit.dayfilm.basket.dto;

import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BasketCreateDto {
    @ApiModelProperty(value="실제 상품(Product) 번호", example="1", required = true)
    private Long productId;
    @ApiModelProperty(value="유저 번호", example="1", required = true)
    private Long userId;
    @ApiModelProperty(value="배송 방법", example="택배수령", required = true)
    private DeliveryMethod method;
    @ApiModelProperty(value="대여 시간", example="2023-02-05T13:00", required = true)
    private LocalDateTime started;
    @ApiModelProperty(value="반납 시간", example="2023-02-07T09:00", required = true)
    private LocalDateTime ended;
}
