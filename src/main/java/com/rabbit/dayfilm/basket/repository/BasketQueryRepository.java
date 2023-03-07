package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketInfo;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BasketQueryRepository {
    Page<BasketResDto.BasketQueryDto> findBasketWithPaging(BasketCond condition);
    Long countBaskets(BasketCond condition);
    List<BasketInfo> findBasketInfos(BasketCond condition);
    List<BasketResDto.BasketQueryDto> findBaskets(BasketCond condition);
}
