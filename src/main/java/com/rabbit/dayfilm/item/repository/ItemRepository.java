package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    @Query("SELECT i.quantity FROM Item i WHERE i.id = :itemId")
    Long findQuantityByItemId(@Param("itemId") Long itemId);
}
