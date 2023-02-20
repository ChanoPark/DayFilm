package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.dto.ItemSearchCondition;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.dto.SelectDetailItemDto;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.store.entity.Store;
import com.rabbit.dayfilm.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<SelectAllItemsDto> selectAllItems(Category category, Pageable pageable);
    SelectDetailItemDto selectItem(Long id);
    public Page<SelectAllItemsDto> selectWriteItems(Category category, Long storeId, Pageable pageable);
    Page<SelectAllItemsDto> selectLikeItems(Category category, Long userId, Pageable pageable);
}
