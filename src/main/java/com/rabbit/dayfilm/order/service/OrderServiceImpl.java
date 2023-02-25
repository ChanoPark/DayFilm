package com.rabbit.dayfilm.order.service;

import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketInfo;
import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.payment.toss.dto.TossPaymentForm;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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
    public TossPaymentForm createOrder(OrderCreateReqDto request) {
        List<Long> basketIds = request.getBasketIds();
        List<BasketInfo> baskets = basketRepository.findBaskets(new BasketCond(basketIds));
        if (baskets.size() == 0) throw new CustomException("주문한 상품이 올바르지 않습니다.");
        else if (baskets.size() != basketIds.size()) throw new CustomException("장바구니 정보가 올바르지 않습니다.");

        //create orderID
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));

        String orderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + user.getId();

        //create orderName;
        String orderName = baskets.get(0).getTitle();
        if (baskets.size() > 1) orderName += " 외 " + (baskets.size()-1) + "건";

        //calculate amount
        int amount = 0;
        for (BasketInfo basket : baskets) {
            basketIds.remove(basket.getBasketId());
            long rentDays = ChronoUnit.DAYS.between(basket.getStarted(), basket.getEnded());

            if (rentDays >= 5 && rentDays < 10) amount += basket.getPricePerFive();
            else if (rentDays >= 10) amount += basket.getPricePerTen();
            else amount += basket.getPricePerOne();
        }
        if (!basketIds.isEmpty()) throw new CustomException("장바구니 정보가 올바르지 않습니다.");
        return new TossPaymentForm(request.getMethod(), amount, orderId, orderName);
    }
}
