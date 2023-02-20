package com.rabbit.dayfilm.item.service;

import com.rabbit.dayfilm.item.entity.*;
import com.rabbit.dayfilm.item.repository.ItemImageRepository;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceImplTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

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
                .orderNumber(2).build();

        items.addItemImage(itemImages1);
        items.addItemImage(itemImages2);


        itemRepository.save(items);
        itemImageRepository.save(itemImages1);
        itemImageRepository.save(itemImages2);
    }

}