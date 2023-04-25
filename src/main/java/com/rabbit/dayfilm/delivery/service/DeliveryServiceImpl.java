package com.rabbit.dayfilm.delivery.service;

import com.rabbit.dayfilm.delivery.dto.DeliveryTracker;
import com.rabbit.dayfilm.delivery.dto.DeliveryTrackerDto;
import com.rabbit.dayfilm.delivery.dto.DeliveryTrackingDto;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.delivery.dto.DeliveryCode;
import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.order.entity.OrderStatus;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderRepository orderRepository;

    WebClient webClient;
    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl("https://apis.tracker.delivery/carriers")
                .defaultHeaders(header -> header.setContentType(MediaType.APPLICATION_JSON))
                .build();
    }

    @Override
    @Cacheable(value = "delivery", key="#trackingNumber", cacheManager = "cacheManager")
    public DeliveryTrackingDto tracking(Long orderPk, DeliveryCode code, String trackingNumber) {
        Order order = orderRepository.findById(orderPk).orElseThrow(() -> new CustomException("주문이 존재하지 않습니다."));
        if (!(order.getStatus() == OrderStatus.DELIVERY || order.getStatus() == OrderStatus.RETURN_DELIVERY))
            throw new CustomException("배송 중인 주문이 아닙니다.");

        DeliveryTrackerDto tracker = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{carrierId}/tracks/{trackId}")
                        .build(code.getId(), trackingNumber)
                )
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, res -> Mono.error(new CustomException("배송 정보가 올바르지 않습니다.")))
                .onStatus(HttpStatus::is5xxServerError, res -> Mono.error(new CustomException("배송 정보를 추적할 수 없습니다.")))
                .bodyToMono(DeliveryTrackerDto.class)
                .block();

        Objects.requireNonNull(tracker, "배송 정보가 존재하지 않습니다.");

        DeliveryTrackingDto.DeliveryProductInfo productInfo = orderRepository.getProductDeliveryInfo(orderPk);
        return new DeliveryTrackingDto(trackingNumber, productInfo, new DeliveryTracker(tracker));
    }
}
