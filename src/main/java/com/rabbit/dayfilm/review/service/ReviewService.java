package com.rabbit.dayfilm.review.service;

import com.rabbit.dayfilm.review.dto.InsertReviewRequestDto;

public interface ReviewService {
    void createReview(InsertReviewRequestDto dto);

    void deleteReview(Long reviewId);
}
