package com.rabbit.dayfilm.payment.toss.object;

import lombok.*;

/**
 * 간편결제 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EasyPay {
    private EasyPayCode provider;
    private Integer amount;
    private Integer discountAmount;
}
