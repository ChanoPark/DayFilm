package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderReturnInfo;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.payment.entity.PayInformation;
import com.rabbit.dayfilm.payment.entity.VirtualAccountRefundInfo;
import com.rabbit.dayfilm.payment.repository.PayRepository;
import com.rabbit.dayfilm.payment.repository.VirtualAccountRefundRepository;
import com.rabbit.dayfilm.payment.toss.object.Method;
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
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;
    private final PayRepository payRepository;
    private final VirtualAccountRefundRepository virtualAccountRefundRepository;

    @Override
    public CodeSet checkNickname(String nickname) {
        if (userRepository.countUserByNickname(nickname) > 0) return CodeSet.DUPLICATE_NICKNAME;
        else return CodeSet.OK;
    }

    @Override
    public OrderListResDto getOrderList(Long userId, Boolean isCanceled, Pageable pageable) {
        Page<OrderListResDto.OrderList> result = orderRepository.getOrderList(userId, isCanceled, pageable);
        return new OrderListResDto(result.getTotalPages(), result.isLast(), result.getContent());
    }

    @Override
    @Transactional
    public CodeSet requestCancelOrder(CancelOrderDto request) {
        Order order = orderRepository.findById(request.getOrderPk()).orElseThrow(() -> new CustomException("주문이 존재하지 않습니다."));

        PayInformation payInformation = payRepository.findByOrderId(order.getOrderId()).orElseThrow(() -> new CustomException("결제 정보가 존재하지 않습니다."));

        if (Method.findMethod(payInformation.getMethod()) == Method.VIRTUAL_ACCOUNT) {
            if (request.getVirtualRefundInfo() == null) throw new CustomException("가상 계좌의 환불 정보가 없습니다.");
            virtualAccountRefundRepository.save(new VirtualAccountRefundInfo(order.getOrderId(), request.getVirtualRefundInfo()));
        }

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
            order.setReturnInfo(new OrderReturnInfo(order, request.getCancelReason(), order.getStatus()));
            order.updateStatus(OrderStatus.CANCEL_WAIT);
            return CodeSet.OK;
        }
    }
}
