package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select new com.rabbit.dayfilm.item.dto.SelectAllItemsDto(i.id, i.storeName, i.title, i.method, i.pricePerOne) from Item i")
    List<Item> findWithPagination(Pageable pageable);
    
}
