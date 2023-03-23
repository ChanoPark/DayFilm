package com.rabbit.dayfilm.store.dto;

import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderListCond {
    @ApiModelProperty(value="가게 번호", example="4")
    private Long storeId;
    @ApiModelProperty(value="주문 상태", example="DELIVERY")
    private OrderStatus status;
    @ApiModelProperty(value="배송 방법", example="PARCEL")
    private DeliveryMethod method;
}
