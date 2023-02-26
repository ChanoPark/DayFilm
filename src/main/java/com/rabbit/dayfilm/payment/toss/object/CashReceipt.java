package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 현금 영수증 정보
 */
@Data
public class CashReceipt {
    private String receiptKey;
    private String type;
    private Long amount;
    private Long taxFreeAmount;
    private String issueNumber;
    private String receiptUrl;
}
