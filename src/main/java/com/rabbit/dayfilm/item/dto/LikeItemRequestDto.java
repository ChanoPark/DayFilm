package com.rabbit.dayfilm.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeItemRequestDto {
    private Long userId;
    private Long itemId;
}
