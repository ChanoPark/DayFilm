package com.rabbit.dayfilm.payment.dto;

import com.rabbit.dayfilm.payment.entity.VirtualAccountRefundInfo;
import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefundReceiveAccount {
    @ApiModelProperty(value="은행 코드", example="3K", required = true)
    private TossCardCode bank;

    @ApiModelProperty(value="계좌 번호", example="123456789", required = true)
    private String accountNumber;

    @ApiModelProperty(value="에금주", example="박찬호", required = true)
    private String holdName;

    public RefundReceiveAccount(VirtualAccountRefundInfo virtualAccountRefundInfo) {
        this.bank = virtualAccountRefundInfo.getBank();
        this.accountNumber = virtualAccountRefundInfo.getAccountNumber();
        this.holdName = virtualAccountRefundInfo.getHoldName();
    }
}
