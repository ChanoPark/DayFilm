package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import org.springframework.data.domain.Page;

public interface BasketQueryRepository {
    Page<BasketResDto.BasketQueryDto> findBasketAll(BasketCond condition);
    Long countBaskets(BasketCond condition);
}
