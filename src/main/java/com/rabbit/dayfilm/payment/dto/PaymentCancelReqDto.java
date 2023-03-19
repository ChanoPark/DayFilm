package com.rabbit.dayfilm.payment.dto;

import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaymentCancelReqDto {
    @ApiModelProperty(value="주문 번호", example="2023030901831", required = true)
    private String orderId;
    @ApiModelProperty(value="환불 사유", example="고객 변심", required = true)
    private String reason;
    @ApiModelProperty(value="주문 번호의 PK", example="[1,2,3,4,5]", required = true)
    private List<Long> orderPrimaryId;
    @ApiModelProperty(value="가상 계좌의 경우 필요한 환불 정보", example="{~~}", required = true)
    private RefundReceiveAccount refundReceiveAccount;

    public static class RefundReceiveAccount {
        @ApiModelProperty(value="은행 코드", example="3K", required = true)
        private TossCardCode bank;

        @ApiModelProperty(value="계좌 번호", example="123456789", required = true)
        private String accountNumber;
        @ApiModelProperty(value="에금주", example="박찬호", required = true)
        private String holdNumber;
    }
}
