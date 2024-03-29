package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 카드사 즉시 할인 프로모션 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Discount {
    private Integer amount;
}
