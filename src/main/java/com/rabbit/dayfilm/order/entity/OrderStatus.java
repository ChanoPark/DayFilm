package com.rabbit.dayfilm.order.entity;

public enum OrderStatus {
    //주문접수
    PAY_WAITING,
    PAY_DONE,
    VISIT_WAIT,
    DELIVERY_WAIT,

    //발송진행
    DELIVERY,

    //발송완료
    RENTAL,
    FINISH,

    //환불
    CANCEL
}
