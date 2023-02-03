package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.ITEM)
public class ItemController {

    @PostMapping("")
    public void createItem(@RequestBody InsertItemRequestDto dto) {

    }
}
