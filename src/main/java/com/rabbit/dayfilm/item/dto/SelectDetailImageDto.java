package com.rabbit.dayfilm.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectDetailImageDto {
    private String imageName;
    private String imagePath;
    private Integer orderNumber;
}
