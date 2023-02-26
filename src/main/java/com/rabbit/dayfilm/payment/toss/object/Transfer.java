package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 계좌이체 정보
 */
@Data
public class Transfer {
    private String bank;
    private String settlementStatus;
}
