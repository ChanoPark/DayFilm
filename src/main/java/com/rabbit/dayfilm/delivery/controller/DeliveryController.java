package com.rabbit.dayfilm.delivery.controller;

import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.delivery.dto.DeliveryTrackingDto;
import com.rabbit.dayfilm.delivery.service.DeliveryService;
import com.rabbit.dayfilm.delivery.dto.DeliveryCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "배송")
@RequestMapping(EndPoint.DELIVERY)
public class DeliveryController {
    private final DeliveryService deliveryService;
    @GetMapping(EndPoint.TRACKING)
    @Operation(summary = "배송 추적", description = "주문 번호와 함께 배송 정보(택배회사,운송장번호)를 넘겨주시면 됩니다.\n택배회사는 배송정보 등록과 동일하게 kr을 제외한 코드를 대문자로 전송해주세요.\n운송장 번호는 숫자만 오거나, 123-456-789와 같은 형태만 가능합니다.")
    public ResponseEntity<DeliveryTrackingDto> tracking(@RequestParam("orderPk") Long orderPk,
                                                        @RequestParam("company") DeliveryCode company,
                                                        @RequestParam("trackingNumber") String trackingNumber) {
        return ResponseEntity.ok().body(deliveryService.tracking(orderPk, company, trackingNumber.replaceAll("-", "")));
    }
}
