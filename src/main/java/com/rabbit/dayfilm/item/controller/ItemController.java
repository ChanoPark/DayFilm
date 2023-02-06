package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.service.ItemSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.ITEM)
public class ItemController {

    private final ItemSerivce itemSerivce;

    @PostMapping("")
    public ResponseEntity<ResponseAbs> createItem(@RequestBody InsertItemRequestDto dto) {
        itemSerivce.createItem(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }
}
