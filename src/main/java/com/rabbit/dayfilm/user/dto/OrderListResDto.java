package com.rabbit.dayfilm.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.payment.toss.object.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListResDto {
    @ApiModelProperty(value="전체 페이지 수", example="15")
    private Integer totalPage;

    @ApiModelProperty(value="마지막 페이지 여부", example="true")
    private Boolean isLast;

    private List<OrderList> contents;

    @Getter
    @NoArgsConstructor
    public static class OrderList {
        @ApiModelProperty(value="주문번호(PK)", example="11")
        private Long orderPk;

        private String orderId;

        @ApiModelProperty(value="아이템 제목", example="캐논 카메라")
        private String title;

        @ApiModelProperty(value="아이템 썸네일 경로", example="https://s3.~")
        private String imagePath;

        @ApiModelProperty(value="주문 날짜", example="2023-03-15T23:11")
        private LocalDateTime created;

        @ApiModelProperty(value="대여 날짜", example="2023-03-16T23:00")
        private LocalDateTime started;

        @ApiModelProperty(value="반납 날짜", example="2023-03-20T23:00")
        private LocalDateTime ended;

        @ApiModelProperty(value="상태", example="PAY_WAITING")
        private OrderStatus status;

        @ApiModelProperty(value="가격", example="25000")
        private Integer price;

        private Method payMethod;

        @QueryProjection
        public OrderList(Long orderPk, String title, String imagePath,
                         LocalDateTime created, LocalDateTime started, LocalDateTime ended, String orderId,
                         OrderStatus status, Integer price, String payMethod) {
            this.orderPk = orderPk;
            this.title = title;
            this.imagePath = imagePath;
            this.created = created;
            this.ended = ended;
            this.orderId = orderId;
            this.started = started;
            this.status = status;
            this.price = price;
            this.payMethod = Method.findMethod(payMethod);
        }
    }
}
