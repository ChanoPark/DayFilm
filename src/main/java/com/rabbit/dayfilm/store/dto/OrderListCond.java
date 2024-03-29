package com.rabbit.dayfilm.store.dto;

import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListCond {
    @ApiModelProperty(value="가게 번호", example="4")
    private Long storeId;
    @ApiModelProperty(value="주문 상태", example="DELIVERY")
    private List<OrderStatus> status;
    @ApiModelProperty(value="배송 방법", example="PARCEL")
    private DeliveryMethod method;
    @NotNull
    private Boolean isCanceled;

    public void setStatus(List<OrderStatus> status) {
        this.status = status;
    }
}
