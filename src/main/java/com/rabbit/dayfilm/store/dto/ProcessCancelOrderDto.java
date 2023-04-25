package com.rabbit.dayfilm.store.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ProcessCancelOrderDto {
    @ApiModelProperty(value="주문 번호(PK)", example="1")
    private Long orderPk;

    @ApiModelProperty(value="환불 승인 여부", example="true")
    private Boolean isAllow;
}
