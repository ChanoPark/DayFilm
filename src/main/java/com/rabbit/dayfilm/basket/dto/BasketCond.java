package com.rabbit.dayfilm.basket.dto;

import com.rabbit.dayfilm.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasketCond {
    private User user;
    private List<Long> basketIds;
    private Long itemId;

    public BasketCond(User user) {
        this.user = user;
    }

    public BasketCond(Long itemId) {
        this.itemId = itemId;
    }

    public BasketCond(User user, Long itemId) {
        this.user = user;
        this.itemId = itemId;
    }
}
