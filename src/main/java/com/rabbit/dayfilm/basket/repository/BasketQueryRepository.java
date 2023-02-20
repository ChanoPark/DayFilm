package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.user.User;

import java.util.List;

public interface BasketQueryRepository {
    List<BasketResDto.BasketQueryDto> findBasket(User user);
}
