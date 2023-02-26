package com.rabbit.dayfilm.order.repository;

import com.rabbit.dayfilm.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
