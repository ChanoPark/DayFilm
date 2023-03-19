package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.dto.SelectProductsDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<SelectProductsDto> selectProduct(Long itemId);
    String selectProductTitle(Long itemId);
}
