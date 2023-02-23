package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.dto.SelectProductsDto;
import com.rabbit.dayfilm.item.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<SelectProductsDto> selectProduct(Long itemId);
}
