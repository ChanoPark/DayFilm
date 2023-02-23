package com.rabbit.dayfilm.review.service;

import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import com.rabbit.dayfilm.review.dto.InsertReviewRequestDto;
import com.rabbit.dayfilm.review.entity.Review;
import com.rabbit.dayfilm.review.repository.ReviewRepository;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    @Override
    public void createReview(InsertReviewRequestDto dto) {
        User findUser = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("해당하는 유저를 찾을 수 없습니다."));

        Item findItem = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new CustomException("해당 번호 아이템이 존재하지 않습니다."));

        Review review = Review.builder()
                .content(dto.getContent())
                .star(dto.getStar())
                .createdDate(LocalDateTime.now())
                .build();

        // 연관관계 매핑
        findUser.addReview(review);
        findItem.addReview(review);

        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
