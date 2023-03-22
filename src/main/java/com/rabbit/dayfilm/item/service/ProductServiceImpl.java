package com.rabbit.dayfilm.item.service;

import com.rabbit.dayfilm.common.util.CopyUtil;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.dto.CreateProductRequest;
import com.rabbit.dayfilm.item.dto.ModifyProductRequestDto;
import com.rabbit.dayfilm.item.dto.SelectProductsDto;
import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.item.entity.ProductStatus;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    @Override
    public List<SelectProductsDto> selectProducts(Long itemId) {
        return productRepository.selectProduct(itemId);
    }

    @Override
    @Transactional
    public void createProduct(Long itemId, CreateProductRequest dto) {
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException("해당 번호에 해당하는 상품을 찾을 수 없습니다."));
        for (int i = 0; i < dto.getQuantity() ; i++) {
            Product product = Product.builder()
                    .productStatus(ProductStatus.AVAILABLE)
                    .build();
            findItem.addProduct(product);
        }

        findItem.addQuantity(dto.getQuantity());
        itemRepository.save(findItem);
    }

    @Override
    @Transactional
    public void modifyProduct(Long productId, ModifyProductRequestDto dto) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("해당하는 제품을 찾을 수 없습니다."));

        // DTO 객체의 null이 아닌 속성을 기존 Item 객체에 복사.
        BeanUtils.copyProperties(dto, findProduct, CopyUtil.getNullPropertyNames(dto));
        productRepository.save(findProduct);
    }
}
