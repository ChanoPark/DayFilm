package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 카드 결제 정보
 */
@Data
public class Card {
    private Long amount;
    private String company;
    private String number;
    private TossCardCode issuerCode;
    private TossCardCode acquirerCode;
    private Integer installmentPlanMonths;
    private String approveNo;
    private Boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String receiptUrl;
    private String acquireStatus;
    private Boolean isInterestFree;
    private String interestPayer;
}
