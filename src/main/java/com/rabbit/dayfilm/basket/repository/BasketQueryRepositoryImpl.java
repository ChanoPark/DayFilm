package com.rabbit.dayfilm.basket.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.user.entity.User;
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
    public List<BasketResDto.BasketQueryDto> findBasket(BasketCond condition) {
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
                        eqUser(condition.getUser())
                )
                .leftJoin(itemImage)
                .on(
                        itemImage.item.eq(item)
                )
                .fetch();
    }

    @Override
    public Long countBaskets(BasketCond condition) {
        return queryFactory
                .select(basket.count())
                .from(basket)
                .where(
                        eqUser(condition.getUser()),
                        eqItemId(condition.getItemId()),
                        inBasketIds(condition.getBasketIds())
                )
                .fetchOne();
    }

    private BooleanExpression eqUser(User user) {
        return user != null ? basket.user.eq(user) : null;
    }

    private BooleanExpression inBasketIds(List<Long> basketIds) {
        return basketIds != null ? (basketIds.size() > 0 ? basket.id.in(basketIds) : null) : null;
    }

    private BooleanExpression eqItemId(Long itemId) {
        return itemId != null ? basket.item.id.eq(itemId) : null;
    }
}
