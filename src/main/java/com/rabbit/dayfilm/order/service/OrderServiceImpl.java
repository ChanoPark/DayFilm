package com.rabbit.dayfilm.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketInfo;
import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.Method;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.payment.toss.dto.TossPaymentForm;
import com.rabbit.dayfilm.payment.toss.object.Payment;
import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Value("${tosspay.test.secretKey}")
    private String secretKey;
    WebClient webClient;
    ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        String encodedKey = new String(Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8)));

        webClient = WebClient.builder()
                .defaultHeaders(header -> header.setBasicAuth(encodedKey))
                .defaultHeaders(header -> header.setContentType(MediaType.APPLICATION_JSON))
                .build();
    }

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
        List<BasketInfo> baskets = basketRepository.findBaskets(new BasketCond(basketIds));
        if (baskets.size() == 0) throw new CustomException("주문한 상품이 올바르지 않습니다.");
        else if (baskets.size() != basketIds.size()) throw new CustomException("장바구니 정보가 올바르지 않습니다.");

        //create orderID
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));

        String orderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + String.format("%03d", user.getId());

        //create orderName
        String orderName = baskets.get(0).getTitle();
        if (baskets.size() > 1) orderName += " 외 " + (baskets.size()-1) + "건";

        int amount = 0;
        for (BasketInfo basket : baskets) {
            //금액 합계 계산
            Long basketId = basket.getBasketId();
            orderedBasketIds.add(basketId);
            basketIds.remove(basketId);

            long rentDays = ChronoUnit.DAYS.between(basket.getStarted(), basket.getEnded());

            if (rentDays >= 5 && rentDays < 10) amount += basket.getPricePerFive();
            else if (rentDays >= 10) amount += basket.getPricePerTen();
            else amount += basket.getPricePerOne();

            //주문 정보 생성
            Address address;
            if (basket.getMethod().equals(Method.PARCEL)) address = request.getAddress();
            else if(basket.getMethod().equals(Method.VISIT)) address = basket.getAddress();
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
                            .build()
            );
        }

        if (!basketIds.isEmpty()) throw new CustomException("장바구니 정보가 올바르지 않습니다.");
        else basketRepository.deleteAllById(orderedBasketIds);

        return new TossPaymentForm(request.getPayMethod().getMethod(), amount, orderId, orderName);
    }

    @Override
    public void paymentConfirm(String paymentKey, String orderId, Integer amount) {
        ObjectNode data = mapper.createObjectNode();
        data.put("paymentKey", paymentKey);
        data.put("orderId", orderId);
        data.put("amount", amount);

        Payment result = webClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CustomException("결제 정보가 올바르지 않습니다.")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CustomException("결제를 진행할 수 없습니다.")))
                .bodyToMono(Payment.class)
                .block();

        /**
         * 1. 주문 상태 변경
         * 2. 결제 엔티티 생성
         */

    }
}
