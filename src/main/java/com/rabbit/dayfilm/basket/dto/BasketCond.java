package com.rabbit.dayfilm.basket.dto;

import com.rabbit.dayfilm.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class BasketCond {
    private User user;
    private List<Long> basketIds;
    private Long itemId;
    private Pageable pageable;

    public BasketCond(User user, Pageable pageable) {
        this.user = user;
        this.pageable = pageable;
    }

    public BasketCond(Long itemId) {
        this.itemId = itemId;
    }
    public BasketCond(List<Long> basketIds) {
        this.basketIds = basketIds;
    }

    public BasketCond(User user, Long itemId) {
        this.user = user;
        this.itemId = itemId;
    }
}
