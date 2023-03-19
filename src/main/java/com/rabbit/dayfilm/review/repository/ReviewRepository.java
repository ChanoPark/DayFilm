package com.rabbit.dayfilm.review.repository;

import com.rabbit.dayfilm.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
