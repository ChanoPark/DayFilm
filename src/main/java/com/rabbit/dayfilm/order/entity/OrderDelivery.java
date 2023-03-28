package com.rabbit.dayfilm.order.entity;

import com.rabbit.dayfilm.delivery.dto.DeliveryCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderDelivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private DeliveryCode code;

    @Column(name = "tracking_number", nullable = false)
    private String trackingNumber;

    public OrderDelivery(Order order, DeliveryCode code, String trackingNumber) {
        this.order = order;
        this.code = code;
        this.trackingNumber = trackingNumber;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}