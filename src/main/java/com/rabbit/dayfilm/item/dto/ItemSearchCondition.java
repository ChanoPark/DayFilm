package com.rabbit.dayfilm.item.dto;

import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.store.entity.Store;
import com.rabbit.dayfilm.user.entity.User;
import lombok.Data;

@Data
public class ItemSearchCondition {
    private Category category;
    private User user;
    private Store store;
}
