package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    @Query("select i from Item i join fetch i.itemImages where i.title = :title")
    Optional<Item> findByTitle(String title);
}
