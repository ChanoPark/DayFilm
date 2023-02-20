package com.rabbit.dayfilm.basket.service;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import com.rabbit.dayfilm.user.User;
import com.rabbit.dayfilm.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void createBasket(BasketCreateDto request) {
        LocalDateTime started = request.getStarted();
        LocalDateTime ended = request.getEnded();

        if (started.isBefore(LocalDateTime.now()) || ended.isBefore(LocalDateTime.now()) || started.isAfter(ended))
            throw new CustomException("대여 시간이 올바르지 않습니다.");

        Basket basket = Basket.builder()
                .user(userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다.")))
                .item(itemRepository.findById(request.getItemId()).orElseThrow(() -> new CustomException("상품이 존재하지 않습니다.")))
                .started(started)
                .ended(ended)
                .method(request.getMethod())
                .build();

        basketRepository.save(basket);
    }

    @Override
    public List<BasketResDto> findAllBasket(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));
        List<BasketResDto.BasketQueryDto> basketList = basketRepository.findBasket(user);

        List<BasketResDto> result = new ArrayList<>();
        for (BasketResDto.BasketQueryDto basket : basketList) {
            int price;

            long rentDays = ChronoUnit.DAYS.between(basket.getStarted(), basket.getEnded());
            if (rentDays >= 5 && rentDays < 10) price = basket.getPricePerFive();
            else if (rentDays >= 10) price = basket.getPricePerTen();
            else price = basket.getPricePerOne();

            result.add(
                    BasketResDto.builder()
                            .basketId(basket.getBasketId())
                            .imagePath(basket.getImagePath())
                            .title(basket.getTitle())
                            .started(basket.getStarted())
                            .ended(basket.getEnded())
                            .price(price)
                            .build()
            );
        }
        return result;
    }
}