package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.RefundStatus;
import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("virtual_account")
public class VirtualAccountPayment extends PayInformation {
    public VirtualAccountPayment(PayInformation payInformation, String orderId, String accountType,
                                 String accountNumber, TossCardCode bankCode, String customerName, LocalDateTime dueDate,
                                 RefundStatus refundStatus, String settlementStatus) {
        super(payInformation);
        this.orderId = orderId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.customerName = customerName;
        this.dueDate = dueDate;
        this.refundStatus = refundStatus;
        this.settlementStatus = settlementStatus;
    }
    @Id
    private String orderId;
    private String accountType;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private TossCardCode bankCode;
    private String customerName;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;
    private String settlementStatus;
}
