package com.rabbit.dayfilm.payment.toss.controller;

import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.order.dto.OrderCreateReqDto;
import com.rabbit.dayfilm.order.service.OrderService;
import com.rabbit.dayfilm.payment.dto.PaymentCancelReqDto;
import com.rabbit.dayfilm.payment.dto.PaymentCancelResDto;
import com.rabbit.dayfilm.payment.toss.dto.TossOrderInfo;
import com.rabbit.dayfilm.payment.toss.dto.TossPaymentForm;
import com.rabbit.dayfilm.payment.toss.object.TossErrorCode;
import com.rabbit.dayfilm.payment.toss.service.TossService;
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
    private final TossService tossService;

    @PostMapping(EndPoint.CREATE)
    @Operation(summary = "토스 결제 생성 시 필요한 객체 만들기", description = "회원 정보와 장바구니 정보를 토대로 결제 정보를 생성합니다. 해당 객체를 갖고 결제 생성 API를 호출해주시면 됩니다.")
    public ResponseEntity<TossPaymentForm> createOrder(@RequestBody OrderCreateReqDto request) {
        return ResponseEntity.ok().body(orderService.createOrder(request));
    }

    @GetMapping(EndPoint.REDIRECT_SUCCESS)
    @Operation(summary = "토스 결제 성공 시 Redirect URL", description = "장바구니에 있는 상품을 지우고, 결제로 넘깁니다.\n상품 주문 시 Redirect되면 결제 승인을 요청합니다.")
    public ResponseEntity<?> payRedirectSuccess(@RequestParam("paymentKey") String paymentKey,
                                                @RequestParam("orderId") String orderId,
                                                @RequestParam("amount") Integer amount) {

        if (tossService.checkOrder(orderId)) {
            tossService.paymentConfirm(paymentKey, orderId, amount); // 결제 정보 반환 필요 -> 화면 구성 먼저.
            return ResponseEntity.ok("승인");
        } else {
            return ResponseEntity.ok("취소");
        }
    }

    @GetMapping(EndPoint.REDIRECT_FAIL)
    @Operation(summary = "토스 결제 실패 시 Redirect URL", description = "주문 정보를 삭제하고 장바구니로 옮깁니다.\n실패 메시지와 주문 정보를 내려드리면 재주문 요청 화면을 보여주면 될 것 같아요.")
    public ResponseEntity<TossOrderInfo> payRedirectFail(@RequestParam("code") TossErrorCode code,
                                                         @RequestParam("message") String message,
                                                         @RequestParam("orderId") String orderId) {
        return ResponseEntity.ok().body(tossService.cancelOrder(orderId, message));
    }
}