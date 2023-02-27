package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("card")
public class CardPay extends PayInformation {
    public CardPay(PayInformation payInformation, String orderId, TossCardCode issuerCode, TossCardCode acquirerCode,
                   String number, Integer installmentPlanMonths, String approveNo, Boolean useCardPoint, String cardType,
                   String ownerType, Boolean isInterestFree, String interestPayer) {
        super(payInformation);
        this.orderId = orderId;
        this.issuerCode = issuerCode;
        this.acquirerCode = acquirerCode;
        this.number = number;
        this.installmentPlanMonths = installmentPlanMonths;
        this.approveNo = approveNo;
        this.useCardPoint = useCardPoint;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.isInterestFree = isInterestFree;
        this.interestPayer = interestPayer;
    }
    @Id
    private String orderId;
    @Enumerated(EnumType.STRING)
    private TossCardCode issuerCode;
    @Enumerated(EnumType.STRING)
    private TossCardCode acquirerCode;
    private String number;
    private Integer installmentPlanMonths;
    private String approveNo;
    private Boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private Boolean isInterestFree;
    private String interestPayer;
}
