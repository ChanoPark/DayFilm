package com.rabbit.dayfilm.item.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.item.dto.ItemSearchCondition;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.QItem;
import com.rabbit.dayfilm.item.entity.QItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.rabbit.dayfilm.item.entity.QItem.*;
import static com.rabbit.dayfilm.item.entity.QItemImage.*;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SelectAllItemsDto> selectAllItems(ItemSearchCondition itemSearchCondition) {
        List<SelectAllItemsDto> itemDto = queryFactory.
                select(Projections.constructor(SelectAllItemsDto.class,
                        item.id.as("itemId"),
                        item.storeName,
                        item.title,
                        item.method,
                        item.pricePerOne,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(itemImage.imagePath)
                                        .from(itemImage)
                                        .where(itemImage.order.eq(1))
                                , "imagePath")))
                .from(item)
                .innerJoin(item.itemImages, itemImage)
                .where(useCategoryEq(itemSearchCondition.getCategory()))
                .fetch();

        return null;
    }

    private BooleanExpression useCategoryEq(Category category) {
        return category == null ? null : item.category.eq(category);
    }
}
