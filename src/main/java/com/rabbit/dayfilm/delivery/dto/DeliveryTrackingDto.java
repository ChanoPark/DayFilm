package com.rabbit.dayfilm.delivery.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "delivery", timeToLive = 3600)
@Getter
public class DeliveryTrackingDto {
    @Id
    private String trackingNumber;
    private DeliveryProductInfo productInfo;
    private DeliveryTracker tracker;

    @Getter
    @NoArgsConstructor
    public static class DeliveryProductInfo {
        @ApiModelProperty(value="상품 제목", example = "캐논 카메라")
        private String title;

        @ApiModelProperty(value="상품 썸네일", example = "1")
        private String imagePath;

        @ApiModelProperty(value="주문 시간", example = "2023-03-28T12:00:23")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime orderDate;

        @QueryProjection
        public DeliveryProductInfo(String title, String imagePath, LocalDateTime orderTime) {
            this.title = title;
            this.imagePath = imagePath;
            this.orderDate = orderTime;
        }
    }
}
