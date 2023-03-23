package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.store.dto.OrderCountResDto;
import com.rabbit.dayfilm.store.dto.OrderListCond;
import com.rabbit.dayfilm.store.dto.OrderListInStoreResDto;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    OrderCountResDto getOrderCount(Long id);
    OrderListInStoreResDto getOrderList(OrderListCond condition, Pageable pageable);
}
