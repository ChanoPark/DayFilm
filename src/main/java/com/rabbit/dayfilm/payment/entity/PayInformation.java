package com.rabbit.dayfilm.payment.entity;

import com.rabbit.dayfilm.payment.toss.object.Method;
import com.rabbit.dayfilm.payment.toss.object.PayStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="method")
public class PayInformation {
    @Id
    private String orderId;
    private String transactionKey;
    private String paymentKey;
    private LocalDateTime requestAt;
    private LocalDateTime approvedAt;
    private String receipt;
    @Enumerated(EnumType.STRING)
    private PayStatus status;
    private Integer amount;
    private Integer suppliedAmount;
    private Double vat;
    private String type;
}
