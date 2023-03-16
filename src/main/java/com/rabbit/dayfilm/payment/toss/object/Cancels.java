package com.rabbit.dayfilm.payment.toss.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

/**
 * 결제 취소 이력
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cancels {
    private Integer cancelAmount;
    private String cancelReason;
    private Integer taxFreeAmount;
    @Nullable
    private Long taxAmount;
    private Integer refundableAmount;
    private Integer easyPayDiscountAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime canceledAt;
    private String transactionKey;
    private Integer taxExemptionAmount;
}
