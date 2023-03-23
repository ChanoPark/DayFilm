package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.store.dto.OrderCountResDto;

public interface StoreService {
    OrderCountResDto getOrderCount(Long id);
}
