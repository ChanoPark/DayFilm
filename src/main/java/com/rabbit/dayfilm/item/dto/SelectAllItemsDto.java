package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class SelectAllItemsDto {
    private Long itemId;
    private String storeName;
    private String title;
    private Method method;
    private Integer pricePerOne;
    private String imagePath;
}
