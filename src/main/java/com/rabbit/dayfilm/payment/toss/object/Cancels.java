package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

import javax.annotation.Nullable;

/**
 * 결제 취소 이력
 */
@Data
public class Cancels {
    private Long cancelAmount;
    private String cancelReason;
    private Long taxFreeAmount;
    @Nullable
    private Long taxAmount;
    private Long refundableAmount;
    private Long easyPayDiscountAmount;
    private String canceledAt;
    private String transactionKey;
}
