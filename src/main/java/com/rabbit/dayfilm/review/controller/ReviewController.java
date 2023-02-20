package com.rabbit.dayfilm.review.controller;


import com.rabbit.dayfilm.common.EndPoint;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.REVIEW)
@Api(tags = "리뷰")
public class ReviewController {

}
