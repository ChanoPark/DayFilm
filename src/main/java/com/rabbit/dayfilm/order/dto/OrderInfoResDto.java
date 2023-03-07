package com.rabbit.dayfilm.order.dto;

import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.store.entity.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderInfoResDto {
    @ApiModelProperty(value="전체 상품 픽업 여부", example="true")
    private Boolean isAllVisit;

    @ApiModelProperty(value="상품 정보 배열", example="[{\"basketId\":15,\"imagePath\":\"https://day-film-bucket.s3.ap-northeast-2.amazonaws.com/테스트 사업장 이름/상품1 모델1\",\"title\":\"상품1\",\"started\":\"2023-04-22T14:00:00\",\"ended\":\"2023-04-25T12:00:00\",\"price\":1000,\"method\":\"PARCEL\"}]")
    private final List<BasketResDto> itemInfo;

    @ApiModelProperty(value="회원의 기본 주소지", example="\"address\":{\"postalCode\":32145,\"address\":\"주소\",\"addressDetail\":\"상세주소\"}")
    private Address address;
    public OrderInfoResDto() {
        this.itemInfo = new ArrayList<>();
    }

    public void setAddressAndIsAllVisit(boolean isAllVisit, Address address) {
        this.isAllVisit = isAllVisit;
        this.address = address;
    }
}
