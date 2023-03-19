package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectAllItemsDto {
    @ApiModelProperty(value="아이템 고유 id(pk)", example="2L")
    private Long itemId;

    @ApiModelProperty(value="판매자 이름", example="A렌탈")
    private String storeName;

    @ApiModelProperty(value="게시글 제목", example="캐논 ~카메라", required = true)
    private String title;

    @ApiModelProperty(value="대여 방법", example="PARCEL", required = true)
    private DeliveryMethod method;

    @ApiModelProperty(value="하루 렌탈가격(1일)", example="10000", required = true)
    private Integer pricePerOne;

    @ApiModelProperty(value="s3에 등록된 이미지 url", example="https:/~")
    private String imagePath;

    @ApiModelProperty(value="리뷰 개수", example="5")
    private Long reviewCount;

    @ApiModelProperty(value="평균 별점", example="4.4")
    private Double starAvg;

    @ApiModelProperty(value="좋아요 개수", example="3")
    private Long likeCount;

}
