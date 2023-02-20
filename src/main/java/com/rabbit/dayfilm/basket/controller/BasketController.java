package com.rabbit.dayfilm.basket.controller;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.service.BasketService;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "장바구니")
@RequestMapping(EndPoint.BASKET)
public class BasketController {
    private final BasketService basketService;

    @PostMapping()
    @Operation(summary = "장바구니 추가", description = "장바구니 생성입니다.")
    public ResponseEntity<SuccessResponse> createBasket(@RequestBody BasketCreateDto request) {
        basketService.createBasket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(CodeSet.OK));
    }
}