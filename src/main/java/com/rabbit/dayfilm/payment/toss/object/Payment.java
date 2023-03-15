package com.rabbit.dayfilm.payment.toss.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
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

    private Integer totalAmount;

    private Integer balanceAmount;

    private PayStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime requestedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime approvedAt;

    private String useEscrow;

    private String transactionKey;

    private String lastTransactionKey;

    private Integer suppliedAmount;

    private Integer vat;

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
