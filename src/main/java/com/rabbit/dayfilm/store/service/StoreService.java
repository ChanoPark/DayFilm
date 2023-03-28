package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.store.dto.OrderCheckDto;
import com.rabbit.dayfilm.store.dto.OrderCountResDto;
import com.rabbit.dayfilm.store.dto.OrderListCond;
import com.rabbit.dayfilm.store.dto.OrderListInStoreResDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    OrderCountResDto getOrderCount(Long id);
    OrderListInStoreResDto getOrderList(OrderListCond condition, Pageable pageable);
    List<OrderCheckDto> checkOrders(List<OrderCheckDto> request);
}
