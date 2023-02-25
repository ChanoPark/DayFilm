package com.rabbit.dayfilm.order.controller;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "주문")
@RequestMapping(EndPoint.ORDER)
public class OrderController {
    private final OrderService orderService;

    @PostMapping(EndPoint.RESERVE)
    @Operation(summary = "상품 예약하기", description = "해당 상품을 장바구니에 추가 한 뒤, 주문 정보로 Redirect됩니다.\n해당 상품이 장바구니에 존재하는 경우, 예약할 때 넣은 정보로 수정됩니다.")
    public ResponseEntity<SuccessResponse> createReservation(@RequestBody BasketCreateDto request) {
        Long basketId = orderService.createReservation(request);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(
                        ServletUriComponentsBuilder.fromPath(EndPoint.ORDER_INFO)
                        .queryParam("userId", request.getUserId())
                        .queryParam("basketId", basketId)
                        .build()
                        .toUri()
                )
                .body(new SuccessResponse(CodeSet.OK));
    }

    @GetMapping(EndPoint.INFO)
    public void getOrderInfo(@ModelAttribute OrderCreateReqDto request) {
        /**
         * 단순히 데이터만 넘겨주면 될지? -> 주소 정보 등 필요한 정보를 취합한 뒤 개발 예정.
         */
    }

}
