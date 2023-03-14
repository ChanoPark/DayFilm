package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 핸드폰 결제 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MobilePhone {
    private String customerMobilePhone;
    private String settlementStatus;
    private String receiptUrl;
}
