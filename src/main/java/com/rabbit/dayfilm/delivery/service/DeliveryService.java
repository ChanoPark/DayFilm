package com.rabbit.dayfilm.delivery.service;

import com.rabbit.dayfilm.delivery.dto.DeliveryCode;
import com.rabbit.dayfilm.delivery.dto.DeliveryTrackingDto;

public interface DeliveryService {
    DeliveryTrackingDto tracking(Long orderPk, DeliveryCode company, String trackingNumber);
}
