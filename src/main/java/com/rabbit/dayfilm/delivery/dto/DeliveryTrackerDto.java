package com.rabbit.dayfilm.delivery.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.rabbit.dayfilm.common.serializer.OffsetToLocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryTrackerDto {
    private FromToInfo from;
    private FromToInfo to;
    private TrackerState state;
    private List<TrackerProgress> progresses;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class FromToInfo {
        @ApiModelProperty(value="이름(보낸사람or받는사람)", example="박*호")
        private String name;

        @ApiModelProperty(value="시간(보낸시간or받은시간)", example="2023-03-28T23:01:00")
        @JsonDeserialize(using = OffsetToLocalDateTimeSerializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime time;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TrackerState {
        @ApiModelProperty(value="배송 상태(코드)", example="DELIVERED")
        private DeliveryStatus id;

        @ApiModelProperty(value="배송 상태(텍스트)", example="배송 완료")
        private String text;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TrackerProgress {
        @ApiModelProperty(value="시간", example="2023-03-28T23:01:00")
        @JsonDeserialize(using = OffsetToLocalDateTimeSerializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime time;

        @ApiModelProperty(value="장소", example="수지직영(오수현)")
        private TrackerLocation location;

        private TrackerStatus status;

        @ApiModelProperty(value="진행 현황", example="보내시는 고객님으로부터 상품을 인수받았습니다.")
        private String description;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TrackerLocation {
        @ApiModelProperty(value="장소", example="수지직영(오수현)")
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TrackerStatus {
        @ApiModelProperty(value="배송 상태(코드)", example="IN_TRANSIT")
        private DeliveryStatus id;

        @ApiModelProperty(value="배송 상태(텍스트)", example="상품이동중")
        private String text;
    }
}
