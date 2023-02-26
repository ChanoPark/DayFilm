package com.rabbit.dayfilm.payment.toss.controller;

import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.order.service.OrderService;
import com.rabbit.dayfilm.payment.toss.dto.TossPaymentForm;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.TOSS)
@Api(tags = "결제: 토스")
@Slf4j
public class TossController {
    private final OrderService orderService;
    @PostMapping(EndPoint.CREATE)
    @Operation(summary = "토스 결제 생성 시 필요한 객체 만들기", description = "회원 정보와 장바구니 정보를 토대로 결제 정보를 생성합니다. 해당 객체를 갖고 결제 생성 API를 호출해주시면 됩니다.")
    public ResponseEntity<TossPaymentForm> createOrder(@RequestBody OrderCreateReqDto request) {
        return ResponseEntity.ok().body(orderService.createOrder(request));
    }

    @GetMapping(EndPoint.REDIRECT_SUCCESS)
    @Operation(summary = "토스 결제 성공 시 Redirect URL\nRedirect되면 결제 승인을 요청합니다.", description = "장바구니에 있는 상품을 지우고, 결제로 넘깁니다.")
    public ResponseEntity<?> payRedirectSuccess(@RequestParam("paymentKey") String paymentKey,
                                   @RequestParam("orderId") String orderId,
                                   @RequestParam("amount") Integer amount) {

        orderService.paymentConfirm(paymentKey, orderId, amount);

        return ResponseEntity.ok("승인");
    }

    @GetMapping(EndPoint.REDIRECT_FAIL)
    public void payRedirectFail() {
        log.info("**FAIL URL GET");
    }

}