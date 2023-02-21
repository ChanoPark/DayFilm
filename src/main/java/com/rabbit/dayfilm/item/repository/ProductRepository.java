package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
