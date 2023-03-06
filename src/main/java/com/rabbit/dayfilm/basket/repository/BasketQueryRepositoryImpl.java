package com.rabbit.dayfilm.basket.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.basket.dto.BasketInfo;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.QBasketInfo;
import com.rabbit.dayfilm.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.rabbit.dayfilm.basket.entity.QBasket.basket;
import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QItemImage.itemImage;

@RequiredArgsConstructor
@Slf4j
public class BasketQueryRepositoryImpl implements BasketQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BasketResDto.BasketQueryDto> findBasketAll(BasketCond condition) {
        List<BasketResDto.BasketQueryDto> content = queryFactory
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
                        item.eq(basket.product.item),
                        eqUser(condition.getUser())
                )
                .leftJoin(itemImage)
                .on(
                        itemImage.item.eq(item)
                )
                .offset(condition.getPageable().getOffset())
                .limit(condition.getPageable().getPageSize())
                .orderBy(basket.id.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(basket.count())
                .from(basket)
                .innerJoin(item)
                .on(
                        item.eq(basket.product.item),
                        eqUser(condition.getUser())
                );

        return PageableExecutionUtils.getPage(content, condition.getPageable(), countQuery::fetchOne);
    }

    @Override
    public List<BasketInfo> findBaskets(BasketCond condition) {
        return queryFactory
                .select(
                        new QBasketInfo(
                                basket.id,
                                basket.product,
                                basket.started,
                                basket.ended,
                                basket.product.item.title,
                                basket.product.item.pricePerOne,
                                basket.product.item.pricePerFive,
                                basket.product.item.pricePerTen,
                                basket.method,
                                item.store.address)
                )
                .from(basket)
                .innerJoin(item)
                .on(basket.product.item.eq(item))
                .where(basket.id.in(condition.getBasketIds()))
                .fetch();
    }

    @Override
    public Long countBaskets(BasketCond condition) {
        return queryFactory
                .select(basket.count())
                .from(basket)
                .where(
                        eqUser(condition.getUser()),
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

}
