package com.rabbit.dayfilm.item.controller;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.dto.CreateItemRequest;
import com.rabbit.dayfilm.item.dto.CreateProductRequest;
import com.rabbit.dayfilm.item.dto.ModifyProductRequestDto;
import com.rabbit.dayfilm.item.dto.SelectProductsDto;
import com.rabbit.dayfilm.item.response.SelectProductResponse;
import com.rabbit.dayfilm.item.service.ProductService;
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
@RequestMapping(EndPoint.PRODUCT)
@Api(tags = "제품")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{itemId}")
    @Operation(summary = "제품 일정 관리", description = "제품에 대해 각 제품 일정과 상태를 반환합니다.")
    public ResponseEntity<SelectProductResponse> getProducts(@PathVariable Long itemId) {
        List<SelectProductsDto> dto = productService.selectProducts(itemId);
        if (dto == null) {
            throw new CustomException("해당 상품에 대한 제품 리스트가 존재하지 않습니다. 관리자에게 문의하세요.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SelectProductResponse(CodeSet.OK, dto));
    }

    @PostMapping("/{itemId}")
    @Operation(summary = "제품 등록", description = "제품에 대해 새로운 제품을 생성합니다.")
    public ResponseEntity<SuccessResponse> createProduct(@PathVariable Long itemId, @RequestBody CreateProductRequest data) {
        productService.createProduct(itemId, data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }


    @PutMapping("/{productId}")
    @Operation(summary = "제품 일정 수정", description = "제품에 대해 제품 일정과 상태를 수정합니다.")
    public ResponseEntity<SuccessResponse> changeProductStatus(@PathVariable Long productId, @RequestBody ModifyProductRequestDto data) {
        productService.modifyProduct(productId, data);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "제품 삭제", description = "제품에 대해 삭제합니다.")
    public ResponseEntity<SuccessResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(CodeSet.OK));
    }
}
