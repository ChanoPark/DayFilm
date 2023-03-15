package com.rabbit.dayfilm.payment.repository;

import com.rabbit.dayfilm.payment.entity.PayInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<PayInformation, String> {
}
