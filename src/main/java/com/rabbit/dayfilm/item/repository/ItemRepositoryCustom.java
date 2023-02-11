package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.dto.ItemSearchCondition;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import org.springframework.data.domain.Page;

public interface ItemRepositoryCustom {
    Page<SelectAllItemsDto> selectAllItems(ItemSearchCondition itemSearchCondition);
}
