package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.service.ItemSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 2. 전체 Item 요약 정보 조회(홈 화면)
 *
 *
 * 3. 카테고리별 전체 Item 요약 정보 조회(카테고리 홈 화면)
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.ITEM)
public class ItemController {

    private final ItemSerivce itemSerivce;

//    @PostMapping("")
//    public ResponseEntity<ResponseAbs> createItem(@RequestBody InsertItemRequestDto dto) {
//        itemSerivce.createItem(dto);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new SuccessResponse(CodeSet.OK));
//    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseAbs> createItem(@RequestPart List<MultipartFile> images, @RequestPart InsertItemRequestDto dto) {
        itemSerivce.createItem(images, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @GetMapping()
    public ResponseEntity<ResponseAbs> selectAllItems(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo
    , @RequestParam(required = false) String category, Pageable pageable) {
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        itemSerivce.selectAllItems();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }
}
