package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 핸드폰 결제 정보
 */
@Data
public class MobilePhone {
    private String customerMobilePhone;
    private String settlementStatus;
    private String receiptUrl;
}
