package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.dto.ModifyItemDto;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.dto.SelectDetailItemDto;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.response.SelectAllItemsResponse;
import com.rabbit.dayfilm.item.response.SelectDetailItemResponse;
import com.rabbit.dayfilm.item.service.ItemSerivce;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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
@Api(tags = "상품")
public class ItemController {

    private final ItemSerivce itemSerivce;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "상품 등록", description = "상품 등록입니다.\n category, itemStatus, method 는 json 형식으로 만들어주시면 됩니다.\n ex) category:{value:'카메라'} \nContentType 확인해서 보내주세요")
    public ResponseEntity<SuccessResponse> createItem(@RequestPart List<MultipartFile> images, @RequestPart InsertItemRequestDto data) {
        itemSerivce.createItem(images, data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @GetMapping()
    @Operation(summary = "전체 상품 조회", description = "전체 상품 조회입니다. \n 쿼리파라미터 형식으로 ?category=카메라&page=1&size=9 보내주시면 됩니다. size는 후에 9로 default 처리 해놓을게요. page 가 0부터 시작해서 -1 해서 보내주시면 됩니다.")
    public ResponseEntity<SelectAllItemsResponse> getAllItems(@RequestParam(required = false) Category category, Pageable pageable) {
        Page<SelectAllItemsDto> dto = itemSerivce.selectAllItems(category, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SelectAllItemsResponse(CodeSet.OK, dto));
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 조회", description = "상품 상세 조회입니다. /items/40 으로 넘겨주시면 pk 값이 40인 아이템 반환합니다.")
    public ResponseEntity<SelectDetailItemResponse> getItem(@PathVariable Long itemId) {
        SelectDetailItemDto dto = itemSerivce.selectDetailItem(itemId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SelectDetailItemResponse(CodeSet.OK, dto));
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "상품 수정입니다.")
    public void modifyItem(@PathVariable Long itemId, @RequestBody ModifyItemDto data) {
    }
}
