package com.rabbit.dayfilm.store.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OrderCheckDto {
    @ApiModelProperty(value="주문 PK(!= orderId)", example="1", required = true)
    private Long orderPk;
    @ApiModelProperty(value="출고 예정일", example="2023-03-30", required = true)
    private LocalDate outgoingDate;
}
