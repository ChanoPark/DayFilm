package com.rabbit.dayfilm.payment.toss.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum Method {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    EASY_PAYMENT("간편결제"),
    PHONE("휴대폰"),
    ACCOUNT_TRANSFER("계좌이체"),
    GIFT_CARD_CULTURE("문화상품권"),
    GIFT_CARD_BOOK_CULTURE("도서문화상품권"),
    GIFT_CARD_GAME_CULTURE("게임문화상품권");

    private final String method;

    @JsonCreator
    public static Method fromString(String method) {
        for (Method m : Method.values()) {
            if (m.getMethod().equals(method)) {
                return m;
            }
        }
        throw new CustomException("결제 정보가 올바르지 않습니다.");
    }

    public static Method findMethod(String method) {
        method = method.toLowerCase();
        for (Method m : Method.values()) {
            if (m.name().toLowerCase().equals(method)) {
                return m;
            }
        }
        throw new CustomException("결제 정보가 올바르지 않습니다.");
    }
}
