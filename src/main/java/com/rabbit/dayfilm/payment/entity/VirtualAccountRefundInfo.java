package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.dto.RefundReceiveAccount;
import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccountRefundInfo {
    @Id
    private String orderId;

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
