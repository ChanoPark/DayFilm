package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderReturnDelivery;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.order.repository.OrderReturnDeliveryRepository;
import com.rabbit.dayfilm.user.dto.CancelOrderDto;
import com.rabbit.dayfilm.user.dto.OrderListResDto;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderReturnDeliveryRepository orderReturnDeliveryRepository;
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    @Override
    public CodeSet checkNickname(String nickname) {
        if (userRepository.countUserByNickname(nickname) > 0) return CodeSet.DUPLICATE_NICKNAME;
        else return CodeSet.OK;
    }

    @Override
    public OrderListResDto getOrderList(Long userId, Boolean isCanceled, Pageable pageable) {
        Page<OrderListResDto.OrderList> content = orderRepository.getOrderList(userId, isCanceled, pageable);
        return new OrderListResDto(content.getTotalPages(), content.isLast(), content.getContent());
    }

    @Override
    @Transactional
    public CodeSet requestCancelOrder(CancelOrderDto request) {
        Order order = orderRepository.findById(request.getOrderPk()).orElseThrow(() -> new CustomException("주문이 존재하지 않습니다."));

        if (order.getStatus() == OrderStatus.PAY_WAITING) {
            productRepository.findById(order.getProductId())
                    .ifPresent(product -> userRepository.findById(order.getUserId())
                            .ifPresent(user ->
                                    basketRepository.save(
                                            Basket.builder()
                                                    .user(user)
                                                    .product(product)
                                                    .started(order.getStarted())
                                                    .ended(order.getEnded())
                                                    .method(order.getMethod())
                                                    .build()
                                    )
                            ));

            orderRepository.delete(order);
            return CodeSet.DELETE_ORDER;
        } else {
            orderReturnDeliveryRepository.save(new OrderReturnDelivery(order, request.getCancelReason()));
            order.updateStatus(OrderStatus.CANCEL_WAIT);
            return CodeSet.OK;
        }
    }
}
