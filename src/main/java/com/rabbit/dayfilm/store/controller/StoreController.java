package com.rabbit.dayfilm.store.controller;

import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.store.dto.OrderCountResDto;
import com.rabbit.dayfilm.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.STORE)
public class StoreController {
    private final StoreService storeService;

    @GetMapping(EndPoint.ORDER_COUNT)
    @Operation(summary = "판매자 주문 수", description = "주문조회 및 배송관리 상단의 결제완료,배송 진행,배송 완료 건수를 반환합니다.")
    public ResponseEntity<OrderCountResDto> getOrderCount(@RequestParam("storeId") Long id) {
        return ResponseEntity.ok().body(storeService.getOrderCount(id));
    }
}
