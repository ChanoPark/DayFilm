package com.rabbit.dayfilm.order.repository;

import com.rabbit.dayfilm.user.dto.OrderListResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryRepository {
    Page<OrderListResDto.OrderList> getOrderList(Long userId, boolean isCanceled, Pageable pageable);
}
