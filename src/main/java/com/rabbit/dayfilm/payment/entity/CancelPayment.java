package com.rabbit.dayfilm.payment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CancelPayment {
    @Id @GeneratedValue
    @Column(name = "cancel_id")
    private Long cancelId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "cancel_reason", nullable = false)
    private String cancelReason;

    @Column(name = "canceled_at", nullable = false)
    private LocalDateTime canceledAt;

    @Column(name = "account_number", nullable = false)
    private Integer cancelAmount;

    @Column(name = "tax_free_amount", nullable = false)
    private Integer taxFreeAmount;

    @Column(name = "tax_exemption_amount", nullable = false)
    private Integer taxExemptionAmount;

    @Column(name = "refundable_amount", nullable = false)
    private Integer refundableAmount;

    @Column(name = "easypay_discount_amount", nullable = false)
    private Integer easyPayDiscountAmount;

    @Column(name = "transaction_key", nullable = false)
    private String transactionKey;
}
