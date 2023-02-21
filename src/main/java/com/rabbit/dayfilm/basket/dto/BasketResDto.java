package com.rabbit.dayfilm.basket.dto;

import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BasketResDto {

    @ApiModelProperty(value="장바구니 번호", example = "1")
    private Long basketId;
    @ApiModelProperty(value="상품 썸네일 경로", example="https://~")
    private String imagePath;
    @ApiModelProperty(value="상품 이름", example="카메라")
    private String title;
    @ApiModelProperty(value="대여 시간", example="2023-02-05T13:00", required = true)
    private LocalDateTime started;
    @ApiModelProperty(value="반납 시간", example="2023-02-07T09:00", required = true)
    private LocalDateTime ended;
    @ApiModelProperty(value="가격", example="15000")
    private Integer price;
    @ApiModelProperty(value="배송 방법", example="택배수령", required = true)
    private Method method;

    @AllArgsConstructor
    @Setter
    @Getter
    public static class BasketQueryDto {
        private Long basketId;
        private String imagePath;
        private String title;
        private LocalDateTime started;
        private LocalDateTime ended;
        private Integer pricePerOne;
        private Integer pricePerFive;
        private Integer pricePerTen;
        private Method method;
    }
}
