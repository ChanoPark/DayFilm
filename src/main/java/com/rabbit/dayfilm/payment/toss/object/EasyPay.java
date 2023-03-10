package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 간편결제 정보
 */
@Getter
@AllArgsConstructor
@ToString
public class EasyPay {
    private EasyPayCode provider;
    private Integer amount;
    private Integer discountAmount;
}
