package com.rabbit.dayfilm.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectDetailImageDto {
    @ApiModelProperty(value="이미지 고유 id", example="2L")
    private Long imageId;

    @ApiModelProperty(value="이미지 이름", example="새롭게 조합할 예정")
    private String imageName;

    @ApiModelProperty(value="이미지 경로", example="https:/~")
    private String imagePath;

    @ApiModelProperty(value="이미지 순서", example="1")
    private Integer orderNumber;
}
