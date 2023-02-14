package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.ItemStatus;
import com.rabbit.dayfilm.item.entity.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectDetailItemDto {
    private String title;
    private Category category;
    private String detail;
    private Integer pricePerOne;
    private Integer pricePerFive;
    private Integer pricePerTen;
    private String brandName;
    private String modelName;
    private Method method;
    private Integer quantity;
}
