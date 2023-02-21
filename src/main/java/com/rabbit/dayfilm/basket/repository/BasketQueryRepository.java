package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketResDto;

import java.util.List;

public interface BasketQueryRepository {
    List<BasketResDto.BasketQueryDto> findBasket(BasketCond condition);
    Long countBaskets(BasketCond condition);
}
