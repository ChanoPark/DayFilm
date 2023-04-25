package com.rabbit.dayfilm.payment.repository;

import com.rabbit.dayfilm.payment.entity.VirtualAccountRefundInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualAccountRefundRepository extends JpaRepository<VirtualAccountRefundInfo, String> {
}
