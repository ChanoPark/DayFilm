package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long>, BasketQueryRepository {
}
