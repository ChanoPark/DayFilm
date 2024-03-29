package com.rabbit.dayfilm.basket.controller;

import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketReqDto;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
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

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "장바구니")
@RequestMapping(EndPoint.BASKET)
public class BasketController {
    private final BasketService basketService;

    @PostMapping()
    @Operation(summary = "장바구니 추가", description = "장바구니 생성입니다.\n실제 상품 번호를 productId에 넣어서 요청하시면 됩니다.")
    public ResponseEntity<SuccessResponse> createBasket(@RequestBody BasketCreateDto request) {
        basketService.createBasket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(CodeSet.OK));
    }

    @GetMapping(EndPoint.ALL)
    @Operation(summary = "장바구니 조회", description = "장바구니 전체 목록 조회입니다.\n페이징 처리 되었고, /all?userId=1&page=0&size=6 과 같은 형태로 요청해주세요.\n상품과 동일하게 0페이지부터 시작됩니다.")
    public ResponseEntity<List<BasketResDto>> findAllBasket(@RequestParam("userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(basketService.findAllBasket(userId, pageable));
    }

    @PostMapping(EndPoint.DELETE)
    @Operation(summary = "장바구니 삭제", description = "장바구니 삭제입니다.\n개수에 상관없이 리스트에 담아주시면 됩니다.\n존재하지 않는 장바구니가 있는 경우, 있는 장바구니도 삭제되지 않고 예외를 반환합니다.")
    public ResponseEntity<SuccessResponse> deleteBaskets(@RequestBody BasketReqDto.DeleteBaskets request) {
        basketService.deleteBaskets(request);
        return ResponseEntity.ok().body(new SuccessResponse(CodeSet.OK));
    }
}