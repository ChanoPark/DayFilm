package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.entity.ProductStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectProductsDto {
    @ApiModelProperty(value="제품 고유 id(pk)", example="1L")
    private Long id;

    @ApiModelProperty(value="제품 현재 상태", example="RENTAL")
    private ProductStatus productStatus;

    @ApiModelProperty(value="일정 시작 날짜", example="2023-02-20")
    private LocalDate startDate;

    @ApiModelProperty(value="일정 종료 날짜", example="2023-02-24")
    private LocalDate endDate;
}
