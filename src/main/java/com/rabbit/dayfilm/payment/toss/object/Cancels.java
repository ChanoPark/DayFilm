package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * 결제 취소 이력
 */
@Getter
@AllArgsConstructor
@ToString
public class Cancels {
    private Integer cancelAmount;
    private String cancelReason;
    private Integer taxFreeAmount;
    @Nullable
    private Long taxAmount;
    private Integer refundableAmount;
    private Integer easyPayDiscountAmount;
    private LocalDateTime canceledAt;
    private String transactionKey;
    private Integer taxExemptionAmount;
}
