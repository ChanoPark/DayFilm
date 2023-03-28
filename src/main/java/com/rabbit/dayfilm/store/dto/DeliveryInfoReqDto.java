package com.rabbit.dayfilm.store.dto;

import com.rabbit.dayfilm.order.entity.DeliveryCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryInfoReqDto {
    @ApiModelProperty(value="주문 PK(!= orderId)", example="1", required = true)
    private Long orderPk;
    @ApiModelProperty(value="택배회사 코드(노션 참고)", example="CUPOST", required = true)
    private DeliveryCode deliveryCompany;
    @ApiModelProperty(value="운송장 번호", example="12345", required = true)
    private String trackingNumber;
}
