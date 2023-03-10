package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 결제창 정보
 */
@Getter
@AllArgsConstructor
@ToString
public class Checkout {
    private String url;
}
