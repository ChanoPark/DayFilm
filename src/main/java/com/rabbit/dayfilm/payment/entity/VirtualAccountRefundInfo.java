package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.dto.RefundReceiveAccount;
import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccountRefundInfo {
    @Id
    private String orderId; // 하나씩 취소할경우 있으면 조회하고 없으면 생성하는 방식

    @Column(name = "bank")
    @Enumerated(EnumType.STRING)
    private TossCardCode bank;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "hold_name")
    private String holdName;

    public VirtualAccountRefundInfo(String orderId, RefundReceiveAccount account) {
        this.orderId = orderId;
        this.bank = account.getBank();
        this.accountNumber = account.getAccountNumber();
        this.holdName = account.getHoldName();
    }
}
