package com.rabbit.dayfilm.payment.toss.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum TossCardCode {
    IBK_BC("3K", "기업비씨", "기업 비씨"),
    GWANGJUBANK("46", "광주", "광주은행"),
    LOTTE("71", "롯데", "롯데카드"),
    KDBBANK("30", "산업", "KDB산업은행"),
    BC("31", "", "비씨카드"),
    SAMSUNG("51", "삼성", "삼성카드"),
    SAEMAUL("38", "새마을", "새마을금고"),
    SHINHAN("41", "신한", "신한카드"),
    SHINHYEOP("62", "신협", "신협"),
    WOORI("33", "우리", "우리카드"),
    POST("37", "우체국", "우체국예금보험"),
    SAVINGBANK("39", "저축", "저축은행중앙회"),
    JEONBUKBANK("35", "전북", "전북은행"),
    JEJUBANK("42", "제주", "제주은행"),
    KAKAOBANK("15", "카카오뱅크", "카카오뱅크"),
    KBANK("3A", "케이뱅크", "케이뱅크"),
    HANA("21", "하나", "하나카드"),
    HYUNDAI("61", "현대", "현대카드"),
    KOOKMIN("11", "국민", "KB국민카드"),
    NONGHYEOP("91", "농협", "NH농협카드"),
    SUHYEOP("34", "수협", "SUHYEOP");

    private String code;
    private String kor;
    private String fullName;

    @JsonCreator
    public static TossCardCode fromString(String code) {
        for (TossCardCode t : TossCardCode.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new CustomException("카드 정보가 올바르지 않습니다.");
    }
}
