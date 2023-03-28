package com.rabbit.dayfilm.order.entity;

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
    CANCEL,

    //사용 완료
    DONE
}
