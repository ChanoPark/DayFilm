package com.rabbit.dayfilm.store.controller;

import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.store.dto.OrderCountResDto;
import com.rabbit.dayfilm.store.dto.OrderListCond;
import com.rabbit.dayfilm.store.dto.OrderListInStoreResDto;
import com.rabbit.dayfilm.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(EndPoint.ORDER_LIST)
    @Operation(summary = "주문 목록 조회", description = "파라미터로 보내는 상태, 수령 방법 등에 따라서 동적인 검색 결과를 반환.")
    public ResponseEntity<OrderListInStoreResDto> getOrderList(@ModelAttribute OrderListCond condition, Pageable pageable) {
        return ResponseEntity.ok(storeService.getOrderList(condition, pageable));
    }
}
