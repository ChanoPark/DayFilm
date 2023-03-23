package com.rabbit.dayfilm.order.entity;

import javax.persistence.*;

@Entity
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
}