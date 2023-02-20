package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.item.dto.*;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.response.SelectAllItemsResponse;
import com.rabbit.dayfilm.item.response.SelectDetailItemResponse;
import com.rabbit.dayfilm.item.service.ItemSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * 2. 전체 Item 요약 정보 조회(홈 화면) 카테고리, 페이징 포함.
 *
 * 3. 해당 Item 상세 페이지 정보 조회.
 *
 * 4. 작성한 Item 목록들 조회.
 *
 * 5. Item 수정.
 *
 * 6. 좋아요 Item list 조회.
 *
 * 7. 좋아요 등록 및 삭제.
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.ITEM)
@Api(tags = "상품")
public class ItemController {

    private final ItemSerivce itemSerivce;


    @GetMapping("/all")
    @Operation(summary = "전체 상품 조회", description = "전체 상품 조회입니다. \n 쿼리파라미터 형식으로 ?category=CAMERA&page=1&size=9 보내주시면 됩니다. size는 후에 9로 default 처리 해놓을게요. page 가 0부터 시작해서 -1 해서 보내주시면 됩니다.")
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

    @GetMapping("/store-write/{storeId}")
    @Operation(summary = "작성한 아이템 조회", description = "작성한 아이템 조회입니다. /items/store-write/3 으로 넘겨주시면 pk 값이 3과 일치하는 가게가 작성한 아이템 목록을 반환합니다.")
    public ResponseEntity<SelectAllItemsResponse> getWriteItems(@PathVariable Long storeId, @RequestParam(required = false) Category category, Pageable pageable) {
        Page<SelectAllItemsDto> dto = itemSerivce.selectWriteItems(category, storeId, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SelectAllItemsResponse(CodeSet.OK, dto));
    }

    @PostMapping(value = "/store-write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "상품 등록", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "data", value = "Item 정보들", required = true, dataTypeClass = InsertItemRequestDto.class, paramType = "form"),
            @ApiImplicitParam(name = "images", value = "Image files", required = true, dataType = "MultipartFile", paramType = "form")
    })
    public ResponseEntity<SuccessResponse> createItem(@RequestPart List<MultipartFile> images, @RequestPart InsertItemRequestDto data) {
        itemSerivce.createItem(images, data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }


    @PutMapping(value = "/store-write/{itemId}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "상품 수정", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "data", value = "Item 정보들", required = true, dataTypeClass = ModifyItemRequestDto.class, paramType = "form"),
            @ApiImplicitParam(name = "images", value = "Image files", required = true, dataType = "MultipartFile", paramType = "form")
    })
    public ResponseEntity<SuccessResponse> modifyItem(@PathVariable Long itemId, @RequestPart List<MultipartFile> images, @RequestPart ModifyItemRequestDto data) {
        itemSerivce.modifyItem(itemId, images, data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @PostMapping("/likes")
    @Operation(summary = "좋아요 등록", description = "게시글을 좋아요 목록에 추가합니다.")
    public ResponseEntity<SuccessResponse> likeItem(@RequestBody LikeItemRequestDto data) {
        itemSerivce.likeItem(data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @GetMapping("/likes/{userId}")
    @Operation(summary = "좋아요 게시글 조회", description = "좋아요 누른 게시글 리스트를 조회합니다.")
    public ResponseEntity<SelectAllItemsResponse> getLikeItems(@PathVariable Long userId, @RequestParam(required = false) Category category, Pageable pageable) {
        Page<SelectAllItemsDto> dto = itemSerivce.selectLikeItems(category, userId, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SelectAllItemsResponse(CodeSet.OK, dto));
    }
}
