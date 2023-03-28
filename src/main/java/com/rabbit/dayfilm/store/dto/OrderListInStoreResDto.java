package com.rabbit.dayfilm.store.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.order.entity.DeliveryCode;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderListInStoreResDto {
    @ApiModelProperty(value="주문 목록", example="[{}]")
    private List<OrderList> orderList;
    @ApiModelProperty(value="전체 페이지 수", example="15")
    private Integer totalPage;
    @ApiModelProperty(value="마지막 페이지 여부", example="true")
    private Boolean isLast;

    @Getter
    public static class OrderList {
        @ApiModelProperty(value="주문 PK(상태 업데이트할 때 넘겨야 할 파라미터)", example="15")
        private Long id;
        @ApiModelProperty(value="주문 상태", example="PAY_DONE")
        private OrderStatus status;
        @ApiModelProperty(value="주문 번호(여러 상품을 한 번에 주문하면 타 상품과 중복 가능)", example="202303023971983")
        private String orderId;
        @ApiModelProperty(value="상품 이름", example="캐논 카메라")
        private String title;
        @ApiModelProperty(value="주문자 닉네임", example="박찬호")
        private String name;
        @ApiModelProperty(value="가격", example="2500")
        private Integer price;
        @ApiModelProperty(value="택배사", example="${미정}")
        private String parcelCompany;
        @ApiModelProperty(value="운송장 번호", example="234567")
        private String trackingNumber;
        @ApiModelProperty(value="출고 예정일", example="2023-03-24")
        private LocalDate outgoingDate;

        @ApiModelProperty(value="수령 방법(PARCEL, VISIT)", example="PARCEL")
        private DeliveryMethod deliveryMethod;

        @QueryProjection
        public OrderList(Long id, OrderStatus status, String orderId, String title, String name,
                         Integer price, DeliveryCode deliveryCode, String trackingNumber,
                         LocalDate outgoingDate, DeliveryMethod deliveryMethod) {
            this.id = id;
            this.status = status;
            this.orderId = orderId;
            this.title = title;
            this.name = name;
            this.price = price;
            this.parcelCompany = deliveryCode != null ? deliveryCode.getTitle() : null;
            this.trackingNumber = trackingNumber;
            this.outgoingDate = outgoingDate;
            this.deliveryMethod = deliveryMethod;
        }
    }
}