package com.rabbit.dayfilm.store.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinishCancelOrderDto {
    @ApiModelProperty(value="주문 번호", example="2023030901831", required = true)
    private String orderId;

    @ApiModelProperty(value="주문 번호의 PK", example="[1,2,3,4,5]", required = true)
    private List<Long> orderPk;
}
