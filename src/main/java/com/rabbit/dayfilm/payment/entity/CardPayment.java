package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.TossCardCode;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("card")
public class CardPayment extends PayInformation {
    public CardPayment(PayInformation payInformation, String orderId, TossCardCode issuerCode, TossCardCode acquirerCode,
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
    @Column(name = "order_id")
    private String orderId;
    @Enumerated(EnumType.STRING)
    @Column(name = "issuer_code", nullable = false)
    private TossCardCode issuerCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "acquirer_code", nullable = false)
    private TossCardCode acquirerCode;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "installment_plan_months", nullable = false)
    private Integer installmentPlanMonths;
    @Column(name = "approve_no", nullable = false)
    private String approveNo;
    @Column(name = "use_card_point", nullable = false)
    private Boolean useCardPoint;
    @Column(name = "card_type", nullable = false)
    private String cardType;
    @Column(name = "owner_type", nullable = false)
    private String ownerType;
    @Column(name = "is_interest_free", nullable = false)
    private Boolean isInterestFree;
    @Column(name = "interest_payer", nullable = false)
    private String interestPayer;
}
