package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.dto.SelectDetailDto;
import com.rabbit.dayfilm.item.dto.SelectDetailItemDto;
import com.rabbit.dayfilm.item.dto.SelectStoreDto;
import com.rabbit.dayfilm.item.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<SelectAllItemsDto> selectAllItems(Category category, Pageable pageable);
    SelectDetailDto selectItem(Long id);
    public List<SelectStoreDto> selectWriteItems(Category category, Long storeId);
    Page<SelectAllItemsDto> selectLikeItems(Category category, Long userId, Pageable pageable);
}
