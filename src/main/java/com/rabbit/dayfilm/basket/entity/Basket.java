package com.rabbit.dayfilm.basket.entity;

import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.entity.Method;
import com.rabbit.dayfilm.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Basket {
    @Id @GeneratedValue
    @Column(name="basket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private LocalDateTime started;

    @Column(nullable = false)
    private LocalDateTime ended;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private Method method;
}
