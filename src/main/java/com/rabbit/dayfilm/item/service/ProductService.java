package com.rabbit.dayfilm.item.service;

import com.rabbit.dayfilm.item.dto.CreateProductRequest;
import com.rabbit.dayfilm.item.dto.ModifyProductRequestDto;
import com.rabbit.dayfilm.item.dto.SelectProductsDto;

import java.util.List;

public interface ProductService {
    public List<SelectProductsDto> selectProducts(Long itemId);

    public void createProduct(Long itemId, CreateProductRequest dto);
    public void modifyProduct(Long productId, ModifyProductRequestDto dto);
    public void deleteProduct(Long productId);
}
