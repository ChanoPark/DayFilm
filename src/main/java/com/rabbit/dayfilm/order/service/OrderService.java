package com.rabbit.dayfilm.order.service;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.payment.toss.dto.TossPaymentForm;

public interface OrderService {
    Long createReservation(BasketCreateDto requestDto);
    TossPaymentForm createOrder(OrderCreateReqDto request);
}
