package com.rabbit.dayfilm.store.service;

import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderDelivery;
import com.rabbit.dayfilm.order.entity.OrderReturnInfo;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.payment.dto.PaymentCancelResDto;
import com.rabbit.dayfilm.payment.dto.RefundReceiveAccount;
import com.rabbit.dayfilm.payment.entity.PayInformation;
import com.rabbit.dayfilm.payment.entity.VirtualAccountRefundInfo;
import com.rabbit.dayfilm.payment.repository.PayRepository;
import com.rabbit.dayfilm.payment.repository.VirtualAccountRefundRepository;
import com.rabbit.dayfilm.payment.toss.object.Method;
import com.rabbit.dayfilm.payment.toss.service.TossService;
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
    private final PayRepository payRepository;
    private final VirtualAccountRefundRepository virtualAccountRefundRepository;
    private final TossService tossService;

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
        if (condition.getIsCanceled()) condition.setStatus(OrderStatus.getCancelStatus());
        else condition.setStatus(OrderStatus.getNotCancelStatus());

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

            order.setOrderDelivery(new OrderDelivery(order, dto.getDeliveryCompany(), dto.getTrackingNumber()));
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

    @Override
    @Transactional
    public void processCancelOrder(List<ProcessCancelOrderDto> request) {
        List<Long> orderPks = request.stream()
                .map(ProcessCancelOrderDto::getOrderPk)
                .collect(Collectors.toList());

        List<Order> orders = orderRepository.findAllById(orderPks);
        for (int i=0; i<orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getStatus() != OrderStatus.CANCEL_WAIT) throw new CustomException("환불이 접수된 주문이 아닙니다.");
            else if (request.get(i).getIsAllow()) {
                order.updateStatus(OrderStatus.CANCEL_DELIVERY);
            } else {
                OrderReturnInfo returnDelivery = order.getReturnInfo();
                order.updateStatus(returnDelivery.getPrevStatus());
            }
        }
    }

    @Override
    public List<PaymentCancelResDto> finishCancelOrder(FinishCancelOrderDto request) {
        if (request.getOrderPk().size() == 0) throw new CustomException("주문 번호가 존재하지 않습니다.");
        String orderId = request.getOrderId();
        List<PaymentCancelResDto> response = new ArrayList<>();

        //가상계좌 환불 정보 조회
        RefundReceiveAccount refundReceiveAccount = null;
        PayInformation payInfo = payRepository.findByOrderId(orderId).orElseThrow(() -> new CustomException("결제 정보가 존재하지 않습니다."));
        if (Method.findMethod(payInfo.getMethod()) == Method.VIRTUAL_ACCOUNT) {
            VirtualAccountRefundInfo virtualAccountRefundInfo = virtualAccountRefundRepository.findById(orderId).orElseThrow(() -> new CustomException("가상계좌의 환불 정보가 존재하지 않습니다."));
            refundReceiveAccount = new RefundReceiveAccount(virtualAccountRefundInfo);
        }

        List<Order> orders = orderRepository.findAllById(request.getOrderPk());
        for (Order order : orders) {
            if (!order.getOrderId().equals(orderId)) throw new CustomException("주문 번호가 일치하지 않습니다.");
            else if(!(order.getStatus() == OrderStatus.CANCEL_DELIVERY || order.getStatus() == OrderStatus.CANCEL_WAIT)) throw new CustomException("환불 진행 중인 주문이 아닙니다.");

            OrderReturnInfo returnInfo = order.getReturnInfo();
            if (returnInfo != null) {
                response.add(tossService.cancelPayment(payInfo, order, refundReceiveAccount, returnInfo.getCancelReason()));
            } else throw new CustomException("환불 정보가 올바르지 않습니다.");
        }

        return response;
    }
}