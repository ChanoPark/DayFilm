package com.rabbit.dayfilm.order.entity;

import com.rabbit.dayfilm.item.entity.DeliveryMethod;
import com.rabbit.dayfilm.store.entity.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="order_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Order {
    @Id @GeneratedValue
    @Column(name="order_primary_id")
    private Long id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "started", nullable = false)
    private LocalDateTime started;

    @Column(name = "ended", nullable = false)
    private LocalDateTime ended;

    @Column(name = "outgoing_date")
    private LocalDate outgoingDate;

    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryMethod method;

    @Embedded
    @Column(name = "address", nullable = false)
    private Address address;

    @Column(name = "shipment_required")
    private String shipmentRequired;

    @Column(name ="price", nullable = false)
    private Integer price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderDelivery orderDelivery;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderReturnDelivery returnDelivery;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
    public void updateOutgoingDate(LocalDate outgoingDate) {
        this.outgoingDate = outgoingDate;
    }
    public void setOrderDelivery(OrderDelivery delivery) {
        this.orderDelivery = delivery;
        delivery.setOrder(this);
    }
    public void setReturnDelivery(OrderReturnDelivery returnDelivery) {
        this.returnDelivery = returnDelivery;
        returnDelivery.setOrder(this);
    }
}
