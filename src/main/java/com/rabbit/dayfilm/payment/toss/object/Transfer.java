package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 계좌이체 정보
 */
@Getter
@AllArgsConstructor
@ToString
public class Transfer {
    private String bank;
    private String settlementStatus;
}
