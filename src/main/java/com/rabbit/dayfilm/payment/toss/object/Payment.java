package com.rabbit.dayfilm.payment.toss.object;

import lombok.*;

import javax.annotation.Nullable;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String version;

    private String paymentKey;

    private String type;

    private String orderId;

    private String orderName;

    private String mId;

    private String currency;

    private Method method;

    private String totalAmount;

    private String balanceAmount;

    private PayStatus status;

    private String requestedAt;

    private String approvedAt;

    private String useEscrow;

    private String transactionKey;

    private String lastTransactionKey;

    private Long suppliedAmount;

    private Long vat;

    private Boolean cultureExpense;

    private Long taxFreeAmount;
    private Integer taxExemptionAmount;

    @Nullable
    private List<Cancels> cancels;

    private Boolean isPartialCancelable;

    @Nullable
    private Card card;

    @Nullable
    private VirtualAccount virtualAccount;

    @Nullable
    private String secret;

    @Nullable
    private MobilePhone mobilePhone;

    @Nullable
    private GiftCertificate giftCertificate;

    @Nullable
    private Transfer transfer;

    private Receipt receipt;
    private Checkout checkout;

    @Nullable
    private EasyPay easyPay;

    private String country;

    @Nullable
    private Failure failure;

    @Nullable
    private CashReceipt cashReceipt;

    @Nullable
    private Discount discount;
}
