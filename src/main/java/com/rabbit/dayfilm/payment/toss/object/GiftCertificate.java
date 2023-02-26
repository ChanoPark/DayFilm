package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 상품권 결제 정보
 */
@Data
public class GiftCertificate {
    private String approveNo;
    private String settlementStatus;
}
