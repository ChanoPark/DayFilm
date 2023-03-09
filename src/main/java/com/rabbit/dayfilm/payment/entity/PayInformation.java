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
    private String orderId;
    private String transactionKey;
    private String paymentKey;
    private LocalDateTime requestAt;
    private LocalDateTime approvedAt;
    private String receipt;
    @Enumerated(EnumType.STRING)
    private PayStatus status;
    private Integer amount;
    private Integer suppliedAmount;
    private Integer vat;
    private String type;
}
