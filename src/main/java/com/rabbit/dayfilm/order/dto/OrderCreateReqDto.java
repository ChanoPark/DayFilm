package com.rabbit.dayfilm.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.payment.toss.object.Method;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateReqDto {
    @JsonCreator
    public OrderCreateReqDto(Long userId, List<Long> basketIds, String payMethod, com.rabbit.dayfilm.item.entity.Method deliveryMethod,
                             Long addressId, String shipmentRequired) {
        this.userId = userId;
        this.basketIds = basketIds;
        this.payMethod = Method.fromString(payMethod);
        this.deliveryMethod = deliveryMethod;
        this.addressId = addressId;
        this.shipmentRequired = shipmentRequired;
    }
    @ApiModelProperty(value="회원 번호", example="1", required = true)
    private Long userId;
    @ApiModelProperty(value="장바구니 번호 목록", example="[1,2,3]", required = true)
    private List<Long> basketIds;
    @ApiModelProperty(value="토스 결제 수단", example="카드", required = true)
    private Method payMethod;
    @ApiModelProperty(value = "배송 수단", example = "PARCEL", required = true)
    private com.rabbit.dayfilm.item.entity.Method deliveryMethod;
    @ApiModelProperty(value="회원 주소 목록의 번호", example="1", required = true)
    private Long addressId;
    @ApiModelProperty(value = "배송 요구사항", example="부재 시 문 앞에 놔주세요.")
    private String shipmentRequired;
}
