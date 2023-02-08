package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectAllItemsDto {
    private Long id;
    private String storeName;
    private String title;
    private String method;
    private Integer pricePerOne;
    private String imagePath;
}
