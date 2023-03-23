package com.rabbit.dayfilm.store.repository;

import com.rabbit.dayfilm.order.entity.OrderStatus;

import java.util.Map;

public interface StoreQueryRepository {
    Map<OrderStatus, Integer> getOrderCountByStatus(long storeId);
}
