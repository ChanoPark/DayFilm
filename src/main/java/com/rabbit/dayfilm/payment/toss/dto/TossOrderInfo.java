package com.rabbit.dayfilm.payment.toss.dto;

import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class TossOrderInfo {
    @ApiModelProperty(value="구매자 닉네임", example="NickA")
    private String nickname;
    @ApiModelProperty(value="구매 실패 사유", example="계좌번호가 올바르지 않습니다.")
    private String message;
    @ApiModelProperty(value="주문 번호", example="2345678")
    private String orderId;
    @ApiModelProperty(value="주문 상품 정보", example="[{\"productTitle\":\"상품명\", \"orderTime\":\"2023-03-05T13:22:00\",\"started\":\"2023-03-10T13:00\",\"ended\":\"2023-03-15T20:00\",\"deliveryMethod\":\"PARCEL\"}]")
    private List<CancelOrderInfo> products;

    @AllArgsConstructor
    @Builder
    @Getter
    public static class CancelOrderInfo {
        @ApiModelProperty(value="상품명", example="상품1")
        private String productTitle;
        @ApiModelProperty(value="주문 시간", example="2023-03-05T13:25:00")
        private LocalDateTime orderTime;
        @ApiModelProperty(value="대여 시간", example="2023-03-10T13:00:00")
        private LocalDateTime started;
        @ApiModelProperty(value="반납 시간", example="2023-03-15T20:00:00")
        private LocalDateTime ended;
        @ApiModelProperty(value="물건 수령 방법", example="VISIT")
        private Method deliveryMethod;
    }
}
