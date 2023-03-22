package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query("SELECT i.title FROM Product p INNER JOIN Item i ON p.item = i INNER JOIN Order o ON o.orderId = :orderId AND o.productId = p.id")
    String getTitleByOrderId(@Param("orderId") String orderId);

    List<Product> getProductsByEndDateLessThan(@Param("endDate") LocalDate endDate);
}
