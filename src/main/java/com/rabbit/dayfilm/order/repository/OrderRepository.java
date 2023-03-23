package com.rabbit.dayfilm.order.repository;

import com.rabbit.dayfilm.order.entity.Order;
import com.rabbit.dayfilm.user.entity.User;
import org.hibernate.NonUniqueResultException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {
    List<Order> findAllByOrderId(@Param("orderId") String orderId);
    Boolean existsByOrderId(@Param("orderId") String orderId);

    @Query("SELECT u FROM Order o INNER JOIN User u ON o.userId = u.id WHERE o.orderId = :orderId")
    User findUserByOrderId(@Param("orderId") String orderId) throws NonUniqueResultException, NoResultException;

    @Transactional
    void deleteAllByOrderId(@Param("orderId") String orderId);
}
