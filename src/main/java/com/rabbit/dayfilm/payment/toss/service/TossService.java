package com.rabbit.dayfilm.payment.toss.service;


import com.rabbit.dayfilm.payment.toss.dto.TossOrderInfo;

public interface TossService {
    boolean paymentConfirm(String paymentKey, String orderId, Integer amount);
    boolean checkOrder(String orderId);

    TossOrderInfo cancelOrder(String orderId, String message);
}
