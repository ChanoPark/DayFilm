package com.rabbit.dayfilm.item.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.rabbit.dayfilm.item.entity.QItem.item;
import static com.rabbit.dayfilm.item.entity.QItemImage.itemImage;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

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
                .title("item1")
                .storeName("test1")
                .category(Category.CAMERA)
                .detail("example")
                .pricePerOne(1000)
                .brandName("testBrand")
                .modelName("testModel")
                .method(Method.VISIT)
                .itemImages(new ArrayList<>())
                .itemStatus(ItemStatus.AVERAGE)
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
                .itemStatus(ItemStatus.AVERAGE)
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

        List<SelectDetailImageDto> imageDto = queryFactory
                .select(Projections.constructor(SelectDetailImageDto.class,
                        itemImage.imageName,
                        itemImage.imagePath,
                        itemImage.orderNumber))
                .from(itemImage)
                .where(itemImage.item.title.eq("item1"))
                .fetch();

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
                        item.quantity
                        ))
                .from(item)
                .where(item.title.eq("item1"))
                .fetchOne();


        assertThat(itemDto.getDetail()).isEqualTo("example");
        assertThat(imageDto.get(0).getImageName()).isEqualTo("testImage");
    }
}