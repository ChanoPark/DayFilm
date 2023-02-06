package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    
}
