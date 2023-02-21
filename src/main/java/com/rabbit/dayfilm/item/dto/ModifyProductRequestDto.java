package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.ProductStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ModifyProductRequestDto {
    @ApiModelProperty(value="제품 현재 상태", example="REPAIR")
    private ProductStatus productStatus;

    @ApiModelProperty(value="일정 시작 날짜", example="2023-02-20")
    private LocalDate startDate;

    @ApiModelProperty(value="일정 종료 날짜", example="2023-02-24")
    private LocalDate endDate;
}
