package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.store.dto.OrderCountResDto;
import com.rabbit.dayfilm.store.dto.OrderListCond;
import com.rabbit.dayfilm.store.dto.OrderListInStoreResDto;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public OrderCountResDto getOrderCount(Long id) {
        Map<OrderStatus, Integer> countMap = storeRepository.getOrderCountByStatus(id);
        return new OrderCountResDto(
                countMap.getOrDefault(OrderStatus.PAY_DONE, 0),
                countMap.getOrDefault(OrderStatus.DELIVERY, 0),
                countMap.getOrDefault(OrderStatus.RENTAL, 0)
        );
    }

    @Override
    public OrderListInStoreResDto getOrderList(OrderListCond condition, Pageable pageable) {
        Page<OrderListInStoreResDto.OrderList> result = storeRepository.getOrderListWithPaging(condition, pageable);

        return new OrderListInStoreResDto(result.getContent(), result.getTotalPages(), result.isLast());
    }
}
