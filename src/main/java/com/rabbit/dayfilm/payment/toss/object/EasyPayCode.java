package com.rabbit.dayfilm.payment.toss.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EasyPayCode {
    TOSSPAY("토스페이"),
    NAVERPAY("네이버페이"),
    SAMSUNGPAY("삼성페이"),
    LPAY("엘페이"),
    KAKAOPAY("카카오페이"),
    PAYCO("페이코"),
    LGPAY("LG페이"),
    SSG("SSG페이");

    private final String kor;

    @JsonCreator
    public static EasyPayCode fromString(String kor) {
        for (EasyPayCode e : EasyPayCode.values()) {
            if (e.getKor().equals(kor)) {
                return e;
            }
        }
        throw new CustomException("간편 결제사 정보가 올바르지 않습니다.");
    }
}
