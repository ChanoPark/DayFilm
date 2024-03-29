package com.rabbit.dayfilm.user.dto;

import com.rabbit.dayfilm.payment.dto.RefundReceiveAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelOrderDto {
    @ApiModelProperty(value="주문 번호(PK)", example="1", required = true)
    private Long orderPk;

    @ApiModelProperty(value="환불 사유", example="물건 상태가 안좋아서.", required = true)
    private String cancelReason;

    @ApiModelProperty(value="가상 계좌 결제의 경우 환불 받을 정보", example="{--}")
    private RefundReceiveAccount virtualRefundInfo;
}
