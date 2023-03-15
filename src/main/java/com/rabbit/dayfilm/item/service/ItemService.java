package com.rabbit.dayfilm.item.service;

import com.rabbit.dayfilm.item.dto.*;
import com.rabbit.dayfilm.item.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    public void createItem(List<MultipartFile> images, List<MultipartFile> infoImages, InsertItemRequestDto dto);
    public Page<SelectAllItemsDto> selectAllItems(Category category, Pageable pageable);
    public SelectDetailItemDto selectDetailItem(Long id);

    public Page<SelectAllItemsDto> selectWriteItems(Category category, Long storeId, Pageable pageable);
    public void modifyItem(Long itemId, List<MultipartFile> images, ModifyItemRequestDto dto);

    public Page<SelectAllItemsDto> selectLikeItems(Category category, Long userId, Pageable pageable);
    public void likeItem(LikeItemRequestDto dto);
    public List<SelectProductsDto> selectProducts(Long itemId);
    public void modifyProduct(Long productId, ModifyProductRequestDto dto);
}
