package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectStoreItemDto {
    @ApiModelProperty(value="아이템 고유 id(pk)", example="2L")
    private Long itemId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectStoreItemDto that = (SelectStoreItemDto) o;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
