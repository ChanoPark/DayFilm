package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.TossCardCode;

import javax.persistence.*;

@Entity
@DiscriminatorValue("card")
public class CardPay extends PayInformation{
    @Id
    private String orderId;
    @Enumerated(EnumType.STRING)
    private TossCardCode issuerCode;
    private String number;
    private Integer installmentPlanMonths;
    private String approveNo;
    private Boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
    private Boolean isInterestFree;
    private String interestPayer;
}
