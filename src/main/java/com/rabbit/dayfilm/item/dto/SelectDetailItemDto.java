package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectDetailItemDto implements Serializable {
    @ApiModelProperty(value="게시글 제목", example="캐논 ~카메라")
    private String title;

    @ApiModelProperty(value="카테고리(json)", example="CAMERA")
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

    @ApiModelProperty(value="대여 방법", example="PARCEL")
    private DeliveryMethod method;

    @ApiModelProperty(value="재고", example="5")
    private Integer quantity;

    @ApiModelProperty(value="우편 번호", example="12345", required = true)
    private Integer postalCode;

    @ApiModelProperty(value="주소", example="서울시 강서구", required = true)
    private String address;

    @ApiModelProperty(value="상세 주소", example="가로공원로76가길 20", required = true)
    private String addressDetail;

}