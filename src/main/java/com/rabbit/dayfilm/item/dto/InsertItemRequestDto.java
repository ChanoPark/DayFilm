package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.ItemStatus;
import com.rabbit.dayfilm.item.entity.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InsertItemRequestDto {
    private Long sellerId;
    private String title;
    private Category category;
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
