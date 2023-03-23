package com.rabbit.dayfilm.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SelectStoreDto {
    private SelectStoreItemDto items;
    private List<SelectStoreProductDto> products;
}
