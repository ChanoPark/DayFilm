package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderDelivery;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.store.dto.*;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

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

    @Override
    @Transactional
    public List<OrderCheckDto> checkOrders(List<OrderCheckDto> request) {
        request.removeIf(dto -> dto.getOrderPk() == null || dto.getOutgoingDate() == null);
        List<Long> orderPks = request.stream()
                .map(OrderCheckDto::getOrderPk)
                .collect(Collectors.toList());

        List<Order> orders = orderRepository.findAllById(orderPks);
        List<OrderCheckDto> response = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            OrderCheckDto dto = request.get(i);
            if (order.getStatus() == OrderStatus.PAY_DONE) {
                response.add(dto);
                order.updateStatus(OrderStatus.RECEIVE_WAIT);
                order.updateOutgoingDate(dto.getOutgoingDate());
            }
        }
        return response;
    }

    @Override
    @Transactional
    public List<DeliveryInfoResDto> updateDeliveryInfo(List<DeliveryInfoReqDto> request) {
        request.removeIf(dto -> dto.getOrderPk() == null || dto.getDeliveryCompany() == null || dto.getTrackingNumber() == null);
        List<Long> orderPks = request.stream()
                .map(DeliveryInfoReqDto::getOrderPk)
                .collect(Collectors.toList());

        List<Order> orders = orderRepository.findAllById(orderPks);
        List<DeliveryInfoResDto> response = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            DeliveryInfoReqDto dto = request.get(i);

            if (!(order.getStatus() == OrderStatus.PAY_DONE || order.getStatus() == OrderStatus.RECEIVE_WAIT)) continue;

            if (order.getOutgoingDate() == null) order.updateOutgoingDate(LocalDate.now());

            order.setDelivery(new OrderDelivery(order, dto.getDeliveryCompany(), dto.getTrackingNumber()));
            response.add(new DeliveryInfoResDto(order.getId(), dto.getDeliveryCompany(), dto.getTrackingNumber(), order.getOutgoingDate()));
        }

        return response;
    }

    @Override
    @Transactional
    public OrderPkDto doneOrder(OrderPkDto request) {
        List<Order> orders = orderRepository.findAllById(request.getOrderPk());

        List<Long> response = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.DELIVERY || order.getStatus() == OrderStatus.RETURN_DELIVERY) {
                productRepository
                        .findById(order.getProductId())
                        .ifPresent(Product::updateProductStatus);

                order.updateStatus(OrderStatus.DONE);
                response.add(order.getId());
            }
        }

        return new OrderPkDto(response);
    }
}