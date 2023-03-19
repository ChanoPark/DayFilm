package com.rabbit.dayfilm.payment.toss.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 결제 실패 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Failure {
    private String code;
    private String message;
}
