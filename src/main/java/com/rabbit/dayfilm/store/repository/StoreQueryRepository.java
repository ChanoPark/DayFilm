package com.rabbit.dayfilm.store.repository;

import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.store.dto.OrderListCond;
import com.rabbit.dayfilm.store.dto.OrderListInStoreResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface StoreQueryRepository {
    Map<OrderStatus, Integer> getOrderCountByStatus(long storeId);
    Page<OrderListInStoreResDto.OrderList> getOrderListWithPaging(OrderListCond condition, Pageable pageable);
}
