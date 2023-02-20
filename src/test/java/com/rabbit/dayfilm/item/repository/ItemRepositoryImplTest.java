package com.rabbit.dayfilm.item.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.dto.SelectDetailImageDto;
import com.rabbit.dayfilm.item.dto.SelectDetailItemDto;
import com.rabbit.dayfilm.item.entity.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QItemImage.itemImage;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryImplTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void 엔티티_초기화() {
        Item items = Item.builder()
                .id(1L)
                .title("item1")
                .storeName("test1")
                .category(Category.CAMERA)
                .detail("example")
                .pricePerOne(1000)
                .brandName("testBrand")
                .modelName("testModel")
                .method(Method.VISIT)
                .use_yn(Boolean.TRUE)
                .quantity(3)
                .build();

        ItemImage itemImages1 = ItemImage.builder()
                .imageName("testImage1")
                .imagePath("/test/a")
                .orderNumber(1).build();

        ItemImage itemImages2 = ItemImage.builder()
                .imageName("testImage2")
                .imagePath("/test/a")
                .orderNumber(1).build();

        items.addItemImage(itemImages1);
        items.addItemImage(itemImages2);


        itemRepository.save(items);
        itemImageRepository.save(itemImages1);
        itemImageRepository.save(itemImages2);

    }

    @Test
        void 엔티티_생성() {
        Item items = Item.builder()
                .title("item1")
                .storeName("test1")
                .category(Category.CAMERA)
                .detail("example")
                .pricePerOne(1000)
                .brandName("testBrand")
                .modelName("testModel")
                .method(Method.VISIT)
                .itemImages(new ArrayList<>())
                .use_yn(Boolean.TRUE)
                .quantity(3)
                .build();

        ItemImage itemImages = ItemImage.builder()
                .imageName("testImage")
                .imagePath("/test/a")
                .orderNumber(1).build();

        items.addItemImage(itemImages);

        itemRepository.save(items);
        itemImageRepository.save(itemImages);

        assertThat(items.getDetail()).isEqualTo("example");
        assertThat(itemImages.getImageName()).isEqualTo("testImage");

    }

    @Test
    void 아이템_상세조회_테스트() {
//
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
                        itemImage.id.as("itemId"),
                        itemImage.imageName,
                        itemImage.imagePath,
                        itemImage.orderNumber))
                        ))
                .from(item)
                .leftJoin(itemImage).on(itemImage.item.id.eq(item.id))
                .where(item.title.eq("item1"))
                .fetch();

        SelectDetailItemDto result = itemDto.stream()
                .findFirst()
                .map(item -> new SelectDetailItemDto(item.getTitle(), item.getCategory(), item.getDetail(), item.getPricePerOne(), item.getPricePerFive(),
                        item.getPricePerTen(), item.getBrandName(), item.getModelName(), item.getMethod(), item.getQuantity(),
                        itemDto.stream().flatMap(i -> i.getImages().stream()).collect(Collectors.toList())))
                .orElse(null);


        assertThat(result.getImages().get(0).getImageName().equals("testImage1"));

    }
}