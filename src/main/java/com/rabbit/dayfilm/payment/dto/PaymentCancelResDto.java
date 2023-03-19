package com.rabbit.dayfilm.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class PaymentCancelResDto {
    @ApiModelProperty(value="주문 번호", example="2023030901831")
    private String orderId;
    private List<ProductInfo> products;

    @Getter
    @AllArgsConstructor
    public static class ProductInfo {
        @ApiModelProperty(value="상품 제목", example="캐논 카메라")
        private String title;
        @ApiModelProperty(value="대여 시간", example="2023-03-10T21:02:02")
        private LocalDateTime started;
        @ApiModelProperty(value="반납 시간", example="2023-03-22T21:02:02")
        private LocalDateTime ended;
        @ApiModelProperty(value="가격", example="5550")
        private Integer price;
    }
}
