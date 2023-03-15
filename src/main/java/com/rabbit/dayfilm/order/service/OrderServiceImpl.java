package com.rabbit.dayfilm.order.service;

import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketInfo;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.order.dto.OrderInfoReqDto;
import com.rabbit.dayfilm.order.dto.OrderInfoResDto;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.payment.toss.dto.TossPaymentForm;
import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserAddressRepository;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserAddressRepository userAddressRepository;

    @Override
    public Long createReservation(BasketCreateDto request) {
        Basket basket = Basket.builder()
                .user(userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다.")))
                .product(productRepository.findById(request.getProductId()).orElseThrow(() -> new CustomException("상품이 존재하지 않습니다.")))
                .started(request.getStarted())
                .ended(request.getEnded())
                .method(request.getMethod())
                .build();
        basketRepository.save(basket);
        return basket.getId();
    }

    @Override
    @Transactional
    public TossPaymentForm createOrder(OrderCreateReqDto request) {
        List<Long> basketIds = request.getBasketIds();
        List<Long> orderedBasketIds = new ArrayList<>();
        List<BasketInfo> baskets = basketRepository.findBasketInfos(new BasketCond(basketIds));
        if (baskets.size() == 0) throw new CustomException("장바구니 정보가 올바르지 않습니다.");
        else if (baskets.size() != basketIds.size()) throw new CustomException("장바구니 정보가 올바르지 않습니다.");

        //create orderID
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));

        String orderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + String.format("%03d", user.getId());

        //create orderName
        String orderName = baskets.get(0).getTitle();
        if (baskets.size() > 1) orderName += " 외 " + (baskets.size() - 1) + "건";

        int amount = 0;
        for (BasketInfo basket : baskets) {
            //금액 합계 계산
            Long basketId = basket.getBasketId();
            orderedBasketIds.add(basketId);
            basketIds.remove(basketId);

            long rentDays = ChronoUnit.DAYS.between(basket.getStarted(), basket.getEnded());

            int price;
            if (rentDays >= 5 && rentDays < 10) price = basket.getPricePerFive();
            else if (rentDays >= 10) price = basket.getPricePerTen();
            else price = basket.getPricePerOne();

            amount += price;
            //주문 정보 생성
            Address address;
            if (basket.getMethod().equals(DeliveryMethod.PARCEL)) {
                address = userAddressRepository.findById(request.getAddressId())
                        .orElseThrow(() -> new CustomException("주소 정보가 올바르지 않습니다."))
                        .getAddress();
            }
            else if (basket.getMethod().equals(DeliveryMethod.VISIT)) address = basket.getAddress();
            else throw new CustomException("주소 정보가 올바르지 않습니다.");

            orderRepository.save(
                    Order.builder()
                            .orderId(orderId)
                            .userId(request.getUserId())
                            .productId(basket.getProduct().getId())
                            .status(OrderStatus.PAY_WAITING)
                            .created(LocalDateTime.now())
                            .started(basket.getStarted())
                            .ended(basket.getEnded())
                            .address(address)
                            .method(request.getDeliveryMethod())
                            .shipmentRequired(request.getShipmentRequired())
                            .price(price)
                            .build()
            );
        }

        if (!basketIds.isEmpty()) throw new CustomException("장바구니 정보가 올바르지 않습니다.");
        else basketRepository.deleteAllById(orderedBasketIds);

        return new TossPaymentForm(request.getPayMethod().getMethod(), amount, orderId, orderName);
    }

    @Override
    public OrderInfoResDto getOrderInfo(OrderInfoReqDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원 정보가 올바르지 않습니다."));

        OrderInfoResDto response = new OrderInfoResDto();
        boolean isAllVisit = true;

        List<BasketResDto.BasketQueryDto> baskets = basketRepository.findBaskets(new BasketCond(user, request.getBasketIds()));
        for (BasketResDto.BasketQueryDto basket : baskets) {
            if (basket.getMethod().equals(DeliveryMethod.PARCEL)) isAllVisit = false;
            int price;

            long rentDays = ChronoUnit.DAYS.between(basket.getStarted(), basket.getEnded());
            if (rentDays >= 5 && rentDays < 10) price = basket.getPricePerFive();
            else if (rentDays >= 10) price = basket.getPricePerTen();
            else price = basket.getPricePerOne();

            response.getItemInfo().add(
                    BasketResDto.builder()
                            .basketId(basket.getBasketId())
                            .imagePath(basket.getImagePath())
                            .title(basket.getTitle())
                            .started(basket.getStarted())
                            .ended(basket.getEnded())
                            .price(price)
                            .method(basket.getMethod())
                            .build()
            );
        }

        Address address = null;
        if (!isAllVisit) address = userAddressRepository.findDefaultAddress(user).orElse(null);

        response.setAddressAndIsAllVisit(isAllVisit, address);
        return response;
    }
}
