package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.PayStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="method")
public class PayInformation {
    public PayInformation(PayInformation payInformation) {
        this.orderId = payInformation.getOrderId();
        this.transactionKey = payInformation.getTransactionKey();
        this.paymentKey = payInformation.getPaymentKey();
        this.requestAt = payInformation.getRequestAt();
        this.approvedAt = payInformation.getApprovedAt();
        this.receipt = payInformation.getReceipt();
        this.status = payInformation.getStatus();
        this.amount = payInformation.getAmount();
        this.suppliedAmount = payInformation.getSuppliedAmount();
        this.vat = payInformation.getVat();
        this.type = payInformation.getType();
    }

    @Id
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "transaction_key", nullable = false, unique = true)
    private String transactionKey;
    @Column(name = "payment_key", nullable = false, unique = true)
    private String paymentKey;
    @Column(name = "request_at", nullable = false)
    private LocalDateTime requestAt;
    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt;
    @Column(name = "receipt", nullable = false)
    private String receipt;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus status;
    @Column(name = "amount", nullable = false)
    private Integer amount;
    @Column(name = "supplied_amount", nullable = false)
    private Integer suppliedAmount;
    @Column(name = "vat", nullable = false)
    private Integer vat;
    @Column(name = "type", nullable = false)
    private String type;

    public void updateStatus(PayStatus status) {
        this.status = status;
    }
}
