package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Product;

public interface ProductRepositoryCustom {
    Product selectProduct(Long itemId);
}
