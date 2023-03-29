package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.EasyPayCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("easy_payment")
public class EasyPayment extends PayInformation {
    public EasyPayment(PayInformation payInformation, String orderId, EasyPayCode easyPayCode, Integer discountAmount) {
        super(payInformation);
        this.orderId = orderId;
        this.easyPayCode = easyPayCode;
        this.discountAmount = discountAmount;
    }

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "easy_pay_code", nullable = false)
    private EasyPayCode easyPayCode;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;
}
