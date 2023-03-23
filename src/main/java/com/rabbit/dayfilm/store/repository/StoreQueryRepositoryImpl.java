package com.rabbit.dayfilm.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.rabbit.dayfilm.item.entity.QProduct.product;
import static com.rabbit.dayfilm.order.entity.QOrder.order;

@RequiredArgsConstructor
public class StoreQueryRepositoryImpl implements StoreQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Map<OrderStatus, Integer> getOrderCountByStatus(long storeId) {
        return queryFactory
                .from(order)
                .innerJoin(product)
                .on(product.id.eq(order.productId))
                .where(product.item.store.id.eq(storeId))
                .groupBy(order.status)
                .transform(
                        groupBy(order.status)
                                .as(order.count().castToNum(Integer.class))
                );
    }
}
