package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    @Modifying
    @Query("DELETE FROM ItemImage i where i.item.id = :itemId")
    void deleteByItemId(@Param("itemId") Long itemId);
}
