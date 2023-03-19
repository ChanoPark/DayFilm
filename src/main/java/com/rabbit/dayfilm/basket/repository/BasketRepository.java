package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long>, BasketQueryRepository {
    Optional<Basket> findBasketByUserAndProduct(@Param("user") User user, @Param("product") Product product);
}
