package com.rabbit.dayfilm.payment.toss.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.payment.entity.CardPay;
import com.rabbit.dayfilm.payment.entity.PayInformation;
import com.rabbit.dayfilm.payment.repository.PayPerMethodRepository;
import com.rabbit.dayfilm.payment.toss.object.Card;
import com.rabbit.dayfilm.payment.toss.object.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossServiceImpl implements TossService {
    private final OrderRepository orderRepository;
    private final PayPerMethodRepository<? super PayInformation> payPerMethodRepository;

    @Value("${tosspay.test.secretKey}")
    private String secretKey;
    WebClient webClient;
    ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        String encodedKey = new String(Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8)));

        webClient = WebClient.builder()
                .defaultHeaders(header -> header.setBasicAuth(encodedKey))
                .defaultHeaders(header -> header.setContentType(MediaType.APPLICATION_JSON))
                .build();
    }

    @Override
    @Transactional
    public boolean paymentConfirm(String paymentKey, String orderId, Integer amount) {
        if (!orderRepository.existsByOrderId(orderId)) return false;

        ObjectNode data = mapper.createObjectNode();
        data.put("paymentKey", paymentKey);
        data.put("orderId", orderId);
        data.put("amount", amount);

        Payment payment = webClient.post()
                .uri("https://api.tosspayments.com/v1/payments/confirm")
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new CustomException("결제 정보가 올바르지 않습니다.")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new CustomException("결제를 진행할 수 없습니다.")))
                .bodyToMono(Payment.class)
                .block();
        Objects.requireNonNull(payment, "결제 승인에 실패했습니다.");

        log.info("결과:{}", payment.toString());

        //주문 상태 변경
        List<Order> orders = orderRepository.findAllByOrderId(orderId);
        for (Order order : orders) {
            order.updateStatus(OrderStatus.PAY_DONE);
        }

        //결제 엔티티 생성
        PayInformation payInformation = PayInformation.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .requestAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .transactionKey(payment.getTransactionKey())
                .receipt(payment.getReceipt().getUrl())
                .status(payment.getStatus())
                .amount(payment.getTotalAmount())
                .suppliedAmount(payment.getSuppliedAmount())
                .vat(payment.getVat())
                .type(payment.getType())
                .build();

        switch (payment.getMethod()) {
            case CARD:
                Objects.requireNonNull(payment.getCard(), "결제 정보가 올바르지 않습니다.");
                createCardPay(orderId, payInformation, payment.getCard());
                break;
        }

        return true;
    }

    private void createCardPay(String orderId, PayInformation payInformation, Card cardInfo) {
        payPerMethodRepository.save(
                new CardPay(
                        payInformation,
                        orderId,
                        cardInfo.getIssuerCode(),
                        cardInfo.getAcquirerCode(),
                        cardInfo.getNumber(),
                        cardInfo.getInstallmentPlanMonths(),
                        cardInfo.getApproveNo(),
                        cardInfo.getUseCardPoint(),
                        cardInfo.getCardType(),
                        cardInfo.getOwnerType(),
                        cardInfo.getIsInterestFree(),
                        cardInfo.getInterestPayer()
                )
        );
    }
}
