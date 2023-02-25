package com.rabbit.dayfilm.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreateReqDto {
    @ApiModelProperty(value="회원 번호", example="1", required = true)
    private Long userId;
    @ApiModelProperty(value="장바구니 번호 목록", example="[1,2,3]", required = true)
    private List<Long> basketIds;
    @ApiModelProperty(value="토스 결제 수단", example="카드", required = true)
    private String method;
}
