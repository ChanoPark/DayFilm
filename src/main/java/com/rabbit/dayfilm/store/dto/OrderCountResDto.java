package com.rabbit.dayfilm.store.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class OrderCountResDto {
    @ApiModelProperty(value="결제 완료 수", example="1")
    private final Integer payDoneCount;
    @ApiModelProperty(value="배송 진행", example="2")
    private final Integer deliveryCount;
    @ApiModelProperty(value="렌탈 중(배송완료)", example="1")
    private final Integer rentalCount;

    public OrderCountResDto(Integer payDoneCount, Integer deliveryCount, Integer rentalCount) {
        this.payDoneCount = payDoneCount;
        this.deliveryCount = deliveryCount;
        this.rentalCount = rentalCount;
    }
}
