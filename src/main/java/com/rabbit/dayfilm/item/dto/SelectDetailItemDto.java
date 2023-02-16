package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.ItemStatus;
import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectDetailItemDto {
    @ApiModelProperty(value="게시글 제목", example="캐논 ~카메라")
    private String title;

    @ApiModelProperty(value="카테고리(json)", example="category:{value:'카메라'}")
    private Category category;

    @ApiModelProperty(value="게시글 상세 설명", example="캐논 ~카메라는 ~~")
    private String detail;

    @ApiModelProperty(value="하루 렌탈가격(1일)", example="10000")
    private Integer pricePerOne;

    @ApiModelProperty(value="하루 렌탈가격(5일 이상)", example="8000")
    private Integer pricePerFive;

    @ApiModelProperty(value="하루 렌탈가격(10일 이상)", example="5000")
    private Integer pricePerTen;

    @ApiModelProperty(value="상품 브랜드 명", example="캐논")
    private String brandName;

    @ApiModelProperty(value="상품 모델 명", example="오토보이 S2")
    private String modelName;

    @ApiModelProperty(value="대여 방법", example="method:{value:'방문수령'}")
    private Method method;

    @ApiModelProperty(value="재고", example="5")
    private Integer quantity;

    @ApiModelProperty(value="images:{}")
    private List<SelectDetailImageDto> images;
}