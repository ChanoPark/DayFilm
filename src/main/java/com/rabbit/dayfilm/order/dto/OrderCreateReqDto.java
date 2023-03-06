package com.rabbit.dayfilm.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rabbit.dayfilm.payment.toss.object.Method;
import com.rabbit.dayfilm.store.entity.Address;
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
                             Integer postalCode, String address, String addressDetail) {
        this.userId = userId;
        this.basketIds = basketIds;
        this.payMethod = Method.fromString(payMethod);
        this.deliveryMethod = deliveryMethod;
        this.address = new Address(postalCode, address, addressDetail);
    }
    @ApiModelProperty(value="회원 번호", example="1", required = true)
    private Long userId;
    @ApiModelProperty(value="장바구니 번호 목록", example="[1,2,3]", required = true)
    private List<Long> basketIds;
    @ApiModelProperty(value="토스 결제 수단", example="카드", required = true)
    private Method payMethod;

    @ApiModelProperty(value = "배송 수단", example = "PARCEL", required = true)
    private com.rabbit.dayfilm.item.entity.Method deliveryMethod;
    @ApiModelProperty(value="주소\n방문 수령인 경우 없어도 됨.", example="{'postalCode'=12345,'address':'경기도 안산시 어쩌구','addressDetail':'201동어쩌구'}", required = true)
    private Address address;
}
