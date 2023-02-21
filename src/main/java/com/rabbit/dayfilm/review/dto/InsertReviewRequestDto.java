package com.rabbit.dayfilm.review.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertReviewRequestDto {
    @ApiModelProperty(value="작성자(유저) 고유값(pk)", example="1L", required = true)
    private Long userId;

    @ApiModelProperty(value="상품 고유값(pk)", example="3L", required = true)
    private Long itemId;

    @ApiModelProperty(value="리뷰 내용", example="너무 친절하고 제품도 너무 좋아요.", required = true)
    private String content;

    @ApiModelProperty(value="별점", example="5", required = true)
    private Integer star;

}
