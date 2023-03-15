package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 상품권 결제 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GiftCertificate {
    private String approveNo;
    private String settlementStatus;
}
