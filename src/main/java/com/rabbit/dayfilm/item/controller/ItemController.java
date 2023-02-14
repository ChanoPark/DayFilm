package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.response.SelectAllItemsResponse;
import com.rabbit.dayfilm.item.service.ItemSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 1. Item 생성
 *
 *
 * 2. 전체 Item 요약 정보 조회(홈 화면) 카테고리, 페이징 포함
 *
 *
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.ITEM)
public class ItemController {

    private final ItemSerivce itemSerivce;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> createItem(@RequestPart List<MultipartFile> images, @RequestPart InsertItemRequestDto dto) {
        itemSerivce.createItem(images, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @GetMapping()
    public ResponseEntity<SelectAllItemsResponse> selectAllItems(@RequestParam(required = false) Category category, Pageable pageable) {
        Page<SelectAllItemsDto> dto = itemSerivce.selectAllItems(category, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SelectAllItemsResponse(CodeSet.OK, dto));
    }
}
