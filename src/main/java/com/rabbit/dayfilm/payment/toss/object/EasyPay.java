package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 간편결제 정보
 */
@Data
public class EasyPay {
    private EasyPayCode provider;
    private Integer amount;
    private Integer discountAmount;
}
