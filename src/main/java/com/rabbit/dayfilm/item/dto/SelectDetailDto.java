package com.rabbit.dayfilm.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectDetailDto {
    @ApiModelProperty(value="아이템 정보")
    private SelectDetailItemDto item;

    @ApiModelProperty(value="이미지 정보들")
    private List<SelectDetailImageDto> images;

    @ApiModelProperty(value="제고 리스트(사용가능한)")
    private List<SelectDetailProductDto> products;

    @ApiModelProperty(value="리뷰 정보들")
    private List<SelectDetailReviewDto> reivews;
}
