package com.rabbit.dayfilm.store.repository;

import com.rabbit.dayfilm.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findStoreByEmail(String email);

    @Transactional
    void deleteStoreByEmail(String email);
}
