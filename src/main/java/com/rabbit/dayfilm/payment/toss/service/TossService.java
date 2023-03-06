package com.rabbit.dayfilm.payment.toss.service;


public interface TossService {
    boolean paymentConfirm(String paymentKey, String orderId, Integer amount);
}
