package com.rabbit.dayfilm.payment.toss.object;

import lombok.Data;

/**
 * 결제 실패 정보
 */
@Data
public class Failure {
    private String code;
    private String message;
}
