package com.rabbit.dayfilm.basket.repository;

import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
