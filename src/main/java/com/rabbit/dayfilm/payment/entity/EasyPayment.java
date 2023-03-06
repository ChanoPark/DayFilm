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
@DiscriminatorValue("easy")
public class EasyPayment extends PayInformation {
    public EasyPayment(PayInformation payInformation, String orderId, EasyPayCode easyPayCode, Integer discountAmount) {
        super(payInformation);
        this.orderId = orderId;
        this.easyPayCode = easyPayCode;
        this.discountAmount = discountAmount;
    }
    @Id
    private String orderId;
    @Enumerated(EnumType.STRING)
    private EasyPayCode easyPayCode;
    private Integer discountAmount;
}
