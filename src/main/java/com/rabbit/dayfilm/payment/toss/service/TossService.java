package com.rabbit.dayfilm.payment.toss.service;


import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.payment.dto.PaymentCancelResDto;
import com.rabbit.dayfilm.payment.dto.RefundReceiveAccount;
import com.rabbit.dayfilm.payment.entity.PayInformation;
import com.rabbit.dayfilm.payment.toss.dto.TossOrderInfo;

public interface TossService {
    boolean paymentConfirm(String paymentKey, String orderId, Integer amount);
    boolean checkOrder(String orderId);
    TossOrderInfo cancelOrder(String orderId, String message);
    PaymentCancelResDto cancelPayment(PayInformation payment, Order order, RefundReceiveAccount refundReceiveAccount, String cancelReason);
}
