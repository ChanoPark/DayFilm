package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 현금 영수증 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CashReceipt {
    private String receiptKey;
    private String type;
    private Long amount;
    private Long taxFreeAmount;
    private String issueNumber;
    private String receiptUrl;
}
