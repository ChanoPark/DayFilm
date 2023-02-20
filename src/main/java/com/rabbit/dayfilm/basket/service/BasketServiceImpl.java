package com.rabbit.dayfilm.basket.service;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import com.rabbit.dayfilm.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}