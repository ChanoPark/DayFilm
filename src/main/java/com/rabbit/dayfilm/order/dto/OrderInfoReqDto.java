package com.rabbit.dayfilm.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OrderInfoReqDto {
    @ApiModelProperty(value="회원 번호", example="1", required = true)
    private Long userId;
    @ApiModelProperty(value="장바구니 번호 배열", example="[1,2]", required = true)
    private List<Long> basketIds;
}
