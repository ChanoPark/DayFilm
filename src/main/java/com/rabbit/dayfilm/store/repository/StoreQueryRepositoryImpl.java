package com.rabbit.dayfilm.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.store.dto.OrderListCond;
import com.rabbit.dayfilm.store.dto.OrderListInStoreResDto;
import com.rabbit.dayfilm.store.dto.QOrderListInStoreResDto_OrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QProduct.product;
import static com.rabbit.dayfilm.order.entity.QOrder.order;
import static com.rabbit.dayfilm.order.entity.QOrderDelivery.orderDelivery;
import static com.rabbit.dayfilm.user.entity.QUser.user;

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

    @Override
    public Page<OrderListInStoreResDto.OrderList> getOrderListWithPaging(OrderListCond condition, Pageable pageable) {
        if (condition.getStoreId() == null) throw new CustomException("가게 번호가 올바르지 않습니다.");

        List<OrderListInStoreResDto.OrderList> content = queryFactory
                .select(
                        new QOrderListInStoreResDto_OrderList(
                                order.id,
                                order.status,
                                order.orderId,
                                item.title,
                                user.nickname,
                                order.price,
                                orderDelivery.code,
                                orderDelivery.trackingNumber,
                                order.outgoingDate,
                                order.method
                        )
                )
                .from(order)
                .leftJoin(orderDelivery)
                .on(orderDelivery.order.eq(order))
                .innerJoin(product)
                .on(product.id.eq(order.productId))
                .innerJoin(item)
                .on(product.item.eq(item))
                .innerJoin(user)
                .on(user.id.eq(order.userId))
                .where(
                        item.store.id.eq(condition.getStoreId()),
                        eqStatus(condition.getStatus()),
                        eqMethod(condition.getMethod())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order)
                .innerJoin(product)
                .on(order.productId.eq(product.id))
                .where(
                        product.item.store.id.eq(condition.getStoreId()),
                        eqStatus(condition.getStatus()),
                        eqMethod(condition.getMethod())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqStatus(List<OrderStatus> status) {
        return status != null ? order.status.in(status) : null;
    }

    private BooleanExpression eqMethod(DeliveryMethod method) {
        return method != null ? order.method.eq(method) : null;
    }

}
