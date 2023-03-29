package com.rabbit.dayfilm.store.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.store.dto.*;
import com.rabbit.dayfilm.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "가게")
@RequestMapping(EndPoint.STORE)
public class StoreController {
    private final StoreService storeService;

    @GetMapping(EndPoint.ORDER_COUNT)
    @Operation(summary = "판매자 주문 수", description = "주문조회 및 배송관리 상단의 결제완료,배송 진행,배송 완료 건수를 반환합니다.")
    public ResponseEntity<OrderCountResDto> getOrderCount(@RequestParam("storeId") Long id) {
        return ResponseEntity.ok().body(storeService.getOrderCount(id));
    }


    @GetMapping(EndPoint.ORDER_LIST)
    @Operation(summary = "주문 목록 조회", description = "파라미터로 보내는 상태, 수령 방법 등에 따라서 동적인 검색 결과를 반환.\nisCanceled는 필수값입니다. 환불 여부입니다.\nisCanceled에 따라서 status가 결정되므로 null로 보내주시면 됩니다.")
    public ResponseEntity<OrderListInStoreResDto> getOrderList(@ModelAttribute OrderListCond condition, Pageable pageable) {
        return ResponseEntity.ok(storeService.getOrderList(condition, pageable));
    }

    @PostMapping(EndPoint.ORDER_CHECK)
    @Operation(summary = "주문 확인(출고일 지정)", description = "해당 주문의 출고일을 지정합니다.\n출고일이 지정된 주문 내용을 반환합니다.")
    public ResponseEntity<List<OrderCheckDto>> checkOrders(@RequestBody List<OrderCheckDto> request) {
        return ResponseEntity.ok().body(storeService.checkOrders(request));
    }

    @PostMapping(EndPoint.ORDER_DELIVERY_UPDATE)
    @Operation(summary = "배송 진행 처리", description = "주문에 대한 배송 정보를 입력합니다.\n출고일 처리가 안되어있으면 오늘 날짜를 기준으로 업데이트합니다.")
    public ResponseEntity<List<DeliveryInfoResDto>> updateDeliveryInfo(@RequestBody List<DeliveryInfoReqDto> request) {
        return ResponseEntity.ok().body(storeService.updateDeliveryInfo(request));
    }

    @PostMapping(EndPoint.ORDER_DONE)
    @Operation(summary = "반납 완료 처리", description = "")
    public ResponseEntity<OrderPkDto> doneOrder(@RequestBody OrderPkDto request) {
        return ResponseEntity.ok().body(storeService.doneOrder(request));
    }

    @PostMapping(EndPoint.ITEM_CANCEL_PROCESS)
    @Operation(summary = "환불 접수", description = "주문번호와 승인여부를 리스트로 받아서 처리합니다.\n승인할 경우 환불 처리 상태로 바꾸고, 거절할 경우 이전 상태로 돌아갑니다.\n잘못된 주문번호가 넘어오면 예외가 발생하고 전체 적용되지 않습니다.")
    public ResponseEntity<SuccessResponse> processCancelOrder(@RequestBody List<ProcessCancelOrderDto> request) {
        storeService.processCancelOrder(request);
        return ResponseEntity.ok().body(new SuccessResponse(CodeSet.OK));
    }
}
