package com.rabbit.dayfilm.order.entity;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    //주문접수
    PAY_WAITING,
    PAY_DONE,

    RECEIVE_WAIT,
    //발송진행
    DELIVERY,

    //발송완료
    RENTAL,
    RETURN_DELIVERY,

    //환불
    CANCEL_WAIT,
    CANCEL,
    CANCEL_DELIVERY,

    //사용 완료
    DONE;

    public static List<OrderStatus> getCancelStatus() {
        return List.of(CANCEL_WAIT, CANCEL_DELIVERY, CANCEL);
    }

    public static List<OrderStatus> getNotCancelStatus() {
        List<OrderStatus> result = Arrays.asList(OrderStatus.values());
        result.removeAll(getCancelStatus());
        return result;
    }
}
