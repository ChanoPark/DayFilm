package com.rabbit.dayfilm.item.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.item.dto.SelectProductsDto;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.item.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QProduct.*;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SelectProductsDto> selectProduct(Long itemId) {
        return queryFactory
                .select(Projections.constructor(SelectProductsDto.class,
                        product.id.as("productId"),
                        product.productStatus,
                        product.startDate,
                        product.endDate))
                .from(product)
                .innerJoin(product.item, item)
                .fetch();
    }

    @Override
    public String selectProductTitle(Long productId) {
        return queryFactory
                .select(product.item.title)
                .from(product)
                .innerJoin(item)
                .on(product.item.eq(item))
                .where(product.id.eq(productId))
                .fetchOne();
    }


}
