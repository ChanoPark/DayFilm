package com.rabbit.dayfilm.review.controller;


import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.review.dto.InsertReviewRequestDto;
import com.rabbit.dayfilm.review.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.REVIEW)
@Api(tags = "리뷰")
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping("/user-write")
    @Operation(summary = "리뷰 생성", description = "리뷰 작성하기입니다.")
    public ResponseEntity<SuccessResponse> createReview(@RequestBody InsertReviewRequestDto data) {
        reviewService.createReview(data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @PostMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제하기 입니다.")
    public ResponseEntity<SuccessResponse> createReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

}
