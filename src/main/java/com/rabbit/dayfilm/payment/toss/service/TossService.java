package com.rabbit.dayfilm.payment.toss.service;


import com.rabbit.dayfilm.payment.dto.PaymentCancelReqDto;
import com.rabbit.dayfilm.payment.dto.PaymentCancelResDto;
import com.rabbit.dayfilm.payment.toss.dto.TossOrderInfo;

public interface TossService {
    boolean paymentConfirm(String paymentKey, String orderId, Integer amount);
    boolean checkOrder(String orderId);

    TossOrderInfo cancelOrder(String orderId, String message);
    PaymentCancelResDto cancelPayment(PaymentCancelReqDto request);
}
