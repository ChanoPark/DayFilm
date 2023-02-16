package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.ItemStatus;
import com.rabbit.dayfilm.item.entity.Method;

public class ModifyItemDto {
    private Long itemId;
    private String title;
    private String detail;
    private Integer pricePerOne;
    private Integer pricePerFive;
    private Integer pricePerTen;
    private String brandName;
    private String modelName;
    private ItemStatus itemStatus;
    private Method method;
    private Integer quantity;

}