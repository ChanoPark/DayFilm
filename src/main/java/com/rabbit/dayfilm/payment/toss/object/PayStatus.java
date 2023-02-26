package com.rabbit.dayfilm.payment.toss.object;

import lombok.Getter;

@Getter
public enum PayStatus {
    READY,
    IN_PROGRESS,
    WAITING_FOR_DEPOSIT,
    DONE,
    CANCELED,
    PARTIAL_CANCELED,
    ABORTED,
    EXPIRED
}
