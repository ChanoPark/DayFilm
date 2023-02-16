package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class SelectAllItemsDto {
    @ApiModelProperty(value="아이템 고유 id(pk)", example="2L")
    private Long itemId;

    @ApiModelProperty(value="판매자 이름", example="A렌탈")
    private String storeName;

    @ApiModelProperty(value="게시글 제목", example="캐논 ~카메라", required = true)
    private String title;

    @ApiModelProperty(value="대여 방법", example="method:{value:'방문수령'}", required = true)
    private Method method;

    @ApiModelProperty(value="하루 렌탈가격(1일)", example="10000", required = true)
    private Integer pricePerOne;

    @ApiModelProperty(value="s3에 등록된 이미지 url", example="https:/~")
    private String imagePath;
}
