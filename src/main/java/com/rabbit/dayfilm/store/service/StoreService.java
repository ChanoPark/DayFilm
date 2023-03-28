package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.store.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {
    OrderCountResDto getOrderCount(Long id);
    OrderListInStoreResDto getOrderList(OrderListCond condition, Pageable pageable);
    List<OrderCheckDto> checkOrders(List<OrderCheckDto> request);
    List<DeliveryInfoResDto> updateDeliveryInfo(List<DeliveryInfoReqDto> request);
    OrderPkDto doneOrder(OrderPkDto request);
}
