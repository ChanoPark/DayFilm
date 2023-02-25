package com.rabbit.dayfilm.payment.toss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Getter
public class TossPaymentForm {
    @ApiModelProperty(value="결제 수단", example="카드", required = true)
    private String method;
    @ApiModelProperty(value="결제 금액 합계", example="50000", required = true)
    private Integer amount;

    @ApiModelProperty(value="주문 번호", example="20201231", required = true)
    private String orderId;

    @ApiModelProperty(value="주문명", example="생수 외 1건", required = true)
    private String orderName;

    @ApiModelProperty(value="결제 성공 시 Redirect URL", example="http://localhost:8080/payment/success", required = true)
    private String successUrl;

    @ApiModelProperty(value="결제 실패 시 Redirect URL", example="http://localhost:8080/payment/fail", required = true)
    private String failUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value="결제창 유형(다른 결제 지원시 필요)", example="DEFAULT")
    private String flowMode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value="간편 결제사 코드(토스페이, 네이버페이 등)", example="TOSSPAY")
    private String easyPay;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value="카드사 코드(토스를 통하지 않고 실제 카드 결제시 필요)", example="3K")
    private String cardCompany;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value="모바일 앱에서 앱으로 돌아올 때 사용(스펙에 존재해서 넣음.)", example="tossapp://")
    private String appScheme;

    public TossPaymentForm(String method, Integer amount, String orderId, String orderName) {
        this.method = method;
        this.amount = amount;
        this.orderId = orderId;
        this.orderName = orderName;
        try {
            this.successUrl = "http://" + InetAddress.getLocalHost().getHostAddress()+":8080/toss/redirect/success";
            this.failUrl = "http://" + InetAddress.getLocalHost().getHostAddress()+":8080/toss/redirect/fail";
        } catch (UnknownHostException e) {
            this.successUrl = "http://localhost:8080/toss/redirect/success";
            this.failUrl = "http://localhost:8080/toss/redirect/fail";
        }
    }
}
