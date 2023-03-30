package com.rabbit.dayfilm.payment.repository;

import com.rabbit.dayfilm.payment.entity.PayInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PayRepository extends JpaRepository<PayInformation, String> {
    Optional<PayInformation> findByOrderId(@Param("orderId") String orderId);
}
