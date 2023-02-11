package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import lombok.Data;

@Data
public class ItemSearchCondition {
    private Integer pageNum;
    private Category category;
}
