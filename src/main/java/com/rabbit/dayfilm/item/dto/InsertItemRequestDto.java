package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertItemRequestDto {
    @ApiModelProperty(value="판매자 고유값(pk)", example="1L", required = true)
    private Long storeId;

    @ApiModelProperty(value="게시글 제목", example="캐논 ~카메라", required = true)
    private String title;

    @ApiModelProperty(value="카테고리(json)", example="CAMERA", required = true)
    private Category category;

    @ApiModelProperty(value="게시글 상세 설명", example="캐논 ~카메라는 ~~", required = true)
    private String detail;

    @ApiModelProperty(value="하루 렌탈가격(1일)", example="10000", required = true)
    private Integer pricePerOne;

    @ApiModelProperty(value="하루 렌탈가격(5일 이상)", example="8000", required = true)
    private Integer pricePerFive;

    @ApiModelProperty(value="하루 렌탈가격(10일 이상)", example="5000", required = true)
    private Integer pricePerTen;

    @ApiModelProperty(value="상품 브랜드 명", example="캐논", required = true)
    private String brandName;

    @ApiModelProperty(value="상품 모델 명", example="오토보이 S2", required = true)
    private String modelName;

    @ApiModelProperty(value="대여 방법", example="PARCEL", required = true)
    private Method method;

    @ApiModelProperty(value="재고", example="5", required = true)
    private Integer quantity;

}
