package com.rabbit.dayfilm.order.entity;

import com.rabbit.dayfilm.delivery.dto.DeliveryCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturnDelivery {
    @Id
    @GeneratedValue
    @Column(name = "return_delivery_id")
    private Long id;

    @OneToOne(mappedBy = "returnDelivery", fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private DeliveryCode code;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column(name = "prev_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus prevStatus;


    public OrderReturnDelivery(Order order, String cancelReason, OrderStatus prevStatus) {
        this.order = order;
        this.cancelReason = cancelReason;
        this.prevStatus = prevStatus;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
