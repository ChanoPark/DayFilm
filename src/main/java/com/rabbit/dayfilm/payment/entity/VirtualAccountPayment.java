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
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "account_type", nullable = false)
    private String accountType;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "bank_code", nullable = false)
    private TossCardCode bankCode;
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status", nullable = false)
    private RefundStatus refundStatus;
    @Column(name = "settlement_status", nullable = false)
    private String settlementStatus;
}
