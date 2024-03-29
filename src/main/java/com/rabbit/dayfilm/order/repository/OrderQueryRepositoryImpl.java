package com.rabbit.dayfilm.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.delivery.dto.DeliveryTrackingDto;
import com.rabbit.dayfilm.delivery.dto.QDeliveryTrackingDto_DeliveryProductInfo;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.user.dto.OrderListResDto;
import com.rabbit.dayfilm.user.dto.QOrderListResDto_OrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QItemImage.itemImage;
import static com.rabbit.dayfilm.item.entity.QProduct.product;
import static com.rabbit.dayfilm.order.entity.QOrder.order;
import static com.rabbit.dayfilm.payment.entity.QPayInformation.payInformation;

@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OrderListResDto.OrderList> getOrderList(Long userId, boolean isCanceled, Pageable pageable) {
        List<OrderListResDto.OrderList> content = queryFactory
                .select(new QOrderListResDto_OrderList(
                        order.id,
                        item.title,
                        itemImage.imagePath,
                        order.created,
                        order.started,
                        order.ended,
                        order.orderId,
                        order.status,
                        order.price,
                        payInformation.method
                ))
                .from(order)
                .innerJoin(product)
                .on(order.productId.eq(product.id))
                .innerJoin(payInformation)
                .on(payInformation.orderId.eq(order.orderId))
                .innerJoin(item)
                .on(product.in(item.products))
                .leftJoin(itemImage)
                .on(
                        itemImage.item.eq(item),
                        itemImage.orderNumber.eq(1)
                )
                .where(
                        order.userId.eq(userId),
                        setStatus(isCanceled)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order.created.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order)
                .where(
                        order.userId.eq(userId),
                        order.status.ne(OrderStatus.CANCEL)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public DeliveryTrackingDto.DeliveryProductInfo getProductDeliveryInfo(Long orderPk) {
        return queryFactory
                .select(new QDeliveryTrackingDto_DeliveryProductInfo(
                        item.title,
                        itemImage.imagePath,
                        order.created
                ))
                .from(order)
                .innerJoin(product)
                .on(product.id.eq(order.productId))
                .innerJoin(item)
                .on(item.eq(product.item))
                .leftJoin(itemImage)
                .on(
                        itemImage.item.eq(item),
                        itemImage.orderNumber.eq(1)
                )
                .where(order.id.eq(orderPk))
                .fetchOne();
    }

    private BooleanExpression setStatus(boolean isCanceled) {
        List<OrderStatus> cancelStatus = OrderStatus.getCancelStatus();
        return isCanceled ? order.status.in(cancelStatus) : order.status.notIn(cancelStatus);
    }
}
