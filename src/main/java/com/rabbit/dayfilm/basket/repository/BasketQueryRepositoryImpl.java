package com.rabbit.dayfilm.basket.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.rabbit.dayfilm.basket.entity.QBasket.basket;
import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QItemImage.itemImage;

@RequiredArgsConstructor
@Slf4j
public class BasketQueryRepositoryImpl implements BasketQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BasketResDto.BasketQueryDto> findBasket(User user) {
        return queryFactory
                .select(Projections.constructor(BasketResDto.BasketQueryDto.class,
                                basket.id,
                                itemImage.imagePath,
                                item.title,
                                basket.started,
                                basket.ended,
                                item.pricePerOne,
                                item.pricePerFive,
                                item.pricePerTen,
                                basket.method
                        )
                )
                .from(basket)
                .innerJoin(item)
                .on(
                        item.eq(basket.item),
                        basket.user.eq(user)
                )
                .leftJoin(itemImage)
                .on(
                        itemImage.item.eq(item)
                )
                .fetch();
    }
}
