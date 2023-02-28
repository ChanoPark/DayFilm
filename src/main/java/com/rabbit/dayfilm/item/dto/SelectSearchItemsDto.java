package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.ItemInfo;
import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SelectSearchItemsDto {
    @ApiModelProperty(value="아이템 고유 id(pk)", example="2L")
    private Long Id;

    @ApiModelProperty(value="판매자 이름", example="A렌탈")
    private String storeName;

    @ApiModelProperty(value="게시글 제목", example="캐논 ~카메라")
    private String title;

    @ApiModelProperty(value="대여 방법", example="PARCEL")
    private Method method;

    @ApiModelProperty(value="카테고리", example="CAMERA")
    private Category category;

    @ApiModelProperty(value="하루 렌탈가격(1일)", example="10000")
    private Integer pricePerOne;

    @ApiModelProperty(value="s3에 등록된 이미지 url", example="https:/~")
    private String imagePath;

    public static SelectSearchItemsDto from(ItemInfo itemInfo) {
        return new SelectSearchItemsDto(itemInfo.getId(), itemInfo.getStoreName(), itemInfo.getTitle(),
                itemInfo.getMethod(), itemInfo.getCategory(), itemInfo.getPricePerOne(), itemInfo.getImagePath());
    }
}
