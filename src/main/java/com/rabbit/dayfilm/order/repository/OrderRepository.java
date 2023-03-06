package com.rabbit.dayfilm.order.repository;

import com.rabbit.dayfilm.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderId(@Param("orderId") String orderId);
    Boolean existsByOrderId(@Param("orderId") String orderId);
}
