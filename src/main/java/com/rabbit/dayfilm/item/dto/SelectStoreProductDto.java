package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectStoreProductDto {
    private ProductStatus productStatus;
    private Long productCount;
}
