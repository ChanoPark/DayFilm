package com.rabbit.dayfilm.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.item.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Product selectProduct(Long itemId) {
        return null;
    }
}
