package com.rabbit.dayfilm.basket.service;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketReqDto;
import com.rabbit.dayfilm.basket.dto.BasketResDto;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BasketService {
    void createBasket(BasketCreateDto request);
    List<BasketResDto> findAllBasket(Long userId, Pageable pageable);
    void deleteBaskets(BasketReqDto.DeleteBaskets request);
}
