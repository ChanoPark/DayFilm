package com.rabbit.dayfilm.item.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.item.dto.*;
import com.rabbit.dayfilm.item.entity.*;
import com.rabbit.dayfilm.review.entity.QReview;
import com.rabbit.dayfilm.store.entity.QStore;
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
import static com.rabbit.dayfilm.item.entity.QProduct.product;
import static com.rabbit.dayfilm.review.entity.QReview.review;
import static com.rabbit.dayfilm.store.entity.QStore.store;

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
                        review.count(),
                        review.star.avg().as("starAvg"),
                        like.count()))
                .from(item)
                .leftJoin(itemImage).on(item.eq(itemImage.item), imageOrderEqOne(), imageEqProduct())
                .leftJoin(review).on(item.eq(review.item))
                .leftJoin(like).on(item.eq(like.item))
                .where(categoryEq(category),
                        useEqY())
                .groupBy(item.id, itemImage.imagePath)
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
    public SelectDetailDto selectItem(Long id) {
        SelectDetailItemDto itemDto = queryFactory
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
                        item.address.postalCode,
                        item.address.address,
                        item.address.addressDetail
                ))
                .from(item)
                .where(itemIdEq(id))
                .fetchOne();

        List<SelectDetailImageDto> imageDto = queryFactory
                .select(Projections.constructor(SelectDetailImageDto.class,
                        itemImage.id.as("imageId"),
                        itemImage.imageName,
                        itemImage.imagePath,
                        itemImage.orderNumber,
                        itemImage.imageType
                ))
                .from(itemImage)
                .where(itemImage.item.id.eq(id))
                .orderBy(itemImage.orderNumber.asc())
                .fetch();

        List<SelectDetailProductDto> productDto = queryFactory
                .select(Projections.constructor(SelectDetailProductDto.class,
                        product.id.as("productId")
                ))
                .from(product)
                .where(product.item.id.eq(id),
                        product.productStatus.eq(ProductStatus.AVAILABLE))
                .fetch();

        List<SelectDetailReviewDto> reviewDto = queryFactory
                .select(Projections.constructor(SelectDetailReviewDto.class,
                        review.user.nickname.as("userName"),
                        review.content,
                        review.star,
                        review.createdDate,
                        review.modifiedDate
                ))
                .from(review)
                .where(review.item.id.eq(id))
                .fetch();

        return new SelectDetailDto(itemDto, imageDto, productDto, reviewDto);
    }

    @Override
    public List<SelectStoreDto> selectWriteItems(Category category, Long storeId) {
//        List<SelectStoreItemDto> items = queryFactory.
//                select(Projections.constructor(SelectStoreItemDto.class,
//                        item.id.as("itemId"),
//                        item.title,
//                        item.method,
//                        item.pricePerOne,
//                        itemImage.imagePath,
//                        review.count(),
//                        review.star.avg().as("starAvg"),
//                        like.count()))
//                .from(item)
//                .leftJoin(itemImage).on(item.eq(itemImage.item), imageOrderEqOne(), imageEqProduct())
//                .leftJoin(review).on(item.eq(review.item))
//                .leftJoin(like).on(item.eq(like.item))
//                .where(categoryEq(category),
//                        useEqY(),
//                        store.id.eq(storeId))
//                .groupBy(item.id, itemImage.imagePath)
//                .fetch();
//
//        List<SelectStoreProductDto> products = queryFactory
//                .select(Projections.constructor(SelectStoreProductDto.class,
//                        product.productStatus,
//                        product.count()))
//                .from(product)
//                .join(product.item, item)
//                .join(item.store, store)
//                .where(store.id.eq(storeId))
//                .groupBy(product.productStatus)
//                .fetch();

        List<Tuple> result = queryFactory.
                select(Projections.constructor(SelectStoreItemDto.class,
                        item.id.as("itemId"),
                        item.title,
                        item.method,
                        item.pricePerOne,
                        itemImage.imagePath,
                        review.count(),
                        review.star.avg().as("starAvg"),
                        like.count()),
                        product.productStatus,
                        product.count())
                .from(item)
                .leftJoin(itemImage).on(item.eq(itemImage.item), imageOrderEqOne(), imageEqProduct())
                .leftJoin(review).on(item.eq(review.item))
                .leftJoin(like).on(item.eq(like.item))
                .leftJoin(product).on(item.eq(product.item))
                .where(categoryEq(category),
                        useEqY(),
                        item.store.id.eq(storeId))
                .groupBy(item.id, itemImage.imagePath, product.productStatus)
                .fetch();

        return result.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, SelectStoreItemDto.class),
                        Collectors.mapping(
                                tuple -> new SelectStoreProductDto(tuple.get(product.productStatus), tuple.get(product.count())),
                                Collectors.toList())))
                .entrySet().stream()
                .map(entry -> new SelectStoreDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
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
    private BooleanExpression imageEqProduct() {
        return itemImage.imageType.eq(ImageType.PRODUCT);
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
