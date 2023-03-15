package com.rabbit.dayfilm.item.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.dto.SelectDetailImageDto;
import com.rabbit.dayfilm.item.dto.SelectDetailItemDto;
import com.rabbit.dayfilm.item.entity.*;
import com.rabbit.dayfilm.review.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.rabbit.dayfilm.item.entity.QItem.*;
import static com.rabbit.dayfilm.item.entity.QItemImage.*;
import static com.rabbit.dayfilm.item.entity.QLike.*;
import static com.rabbit.dayfilm.review.entity.QReview.review;

@RequiredArgsConstructor
@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SelectAllItemsDto> selectAllItems(Category category, Pageable pageable) {
        List<SelectAllItemsDto> content = queryFactory.
                select(Projections.constructor(SelectAllItemsDto.class,
                        item.id.as("itemId"),
                        item.storeName,
                        item.title,
                        item.method,
                        item.pricePerOne,
                        itemImage.imagePath,
                        review.count().as("reviewCount"),
                        review.star.avg().as("starAvg")))
                .from(item)
                .innerJoin(item.itemImages, itemImage)
                .innerJoin(item.reviews, review)
                .where(categoryEq(category),
                        imageOrderEqOne(),
                        useEqY())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .where(categoryEq(category),
                        useEqY());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public SelectDetailItemDto selectItem(Long id) {
        List<SelectDetailItemDto> itemDto = queryFactory
                .select(Projections.constructor(SelectDetailItemDto.class,
                        item.title,
                        item.category,
                        item.detail,
                        item.pricePerOne,
                        item.pricePerFive,
                        item.pricePerTen,
                        item.brandName,
                        item.modelName,
                        item.method,
                        item.quantity,
                        Projections.list(Projections.constructor(SelectDetailImageDto.class,
                                itemImage.id.as("imageId"),
                                itemImage.imageName,
                                itemImage.imagePath,
                                itemImage.orderNumber))
                ))
                .from(item)
                .leftJoin(item.itemImages, itemImage)
                .where(itemIdEq(id))
                .fetch();

        return itemDto.stream()
                .findFirst()
                .map(item -> new SelectDetailItemDto(item.getTitle(), item.getCategory(), item.getDetail(), item.getPricePerOne(), item.getPricePerFive(),
                        item.getPricePerTen(), item.getBrandName(), item.getModelName(), item.getMethod(), item.getQuantity(),
                        itemDto.stream().flatMap(i -> i.getImages().stream()).collect(Collectors.toList())))
                .orElse(null);
    }

    @Override
    public Page<SelectAllItemsDto> selectWriteItems(Category category, Long storeId, Pageable pageable) {
        List<SelectAllItemsDto> content = queryFactory.
                select(Projections.constructor(SelectAllItemsDto.class,
                        item.id.as("itemId"),
                        item.storeName,
                        item.title,
                        item.method,
                        item.pricePerOne,
                        itemImage.imagePath))
                .from(item)
                .innerJoin(item.itemImages, itemImage)
                .where(storeIdEq(storeId),
                        imageOrderEqOne())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .where(storeIdEq(storeId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<SelectAllItemsDto> selectLikeItems(Category category, Long userId, Pageable pageable) {
        List<SelectAllItemsDto> content = queryFactory.
                select(Projections.constructor(SelectAllItemsDto.class,
                        item.id.as("itemId"),
                        item.storeName,
                        item.title,
                        item.method,
                        item.pricePerOne,
                        itemImage.imagePath))
                .from(like)
                .innerJoin(like.item, item)
                .innerJoin(item.itemImages, itemImage)
                .where(like.user.id.eq(userId),
                        categoryEq(category),
                        imageOrderEqOne())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(like)
                .innerJoin(like.item, item)
                .innerJoin(item.itemImages, itemImage)
                .where(like.user.id.eq(userId),
                        categoryEq(category));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }

    private BooleanExpression itemIdEq(Long id) {
        return id == null ? null : item.id.eq(id);
    }
    private BooleanExpression storeIdEq(Long storeId) {
        return storeId == null ? null : item.store.id.eq(storeId);
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : item.category.eq(category);
    }

    private BooleanExpression imageOrderEqOne() {
        return itemImage.orderNumber.eq(1);
    }

    private BooleanExpression useEqY() {
        return item.use_yn.eq(Boolean.TRUE);
    }
}
