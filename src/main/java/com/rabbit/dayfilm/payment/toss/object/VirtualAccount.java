package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 가상계좌 결제 정보
 */
@Data
public class VirtualAccount {
    private String accountType;
    private String accountNumber;
    private String bank;
    private String customerName;
    private String dueDate;
    private String refundStatus;
    private Boolean expired;
    private String settlementsStatus;
}
