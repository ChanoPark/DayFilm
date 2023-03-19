package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
