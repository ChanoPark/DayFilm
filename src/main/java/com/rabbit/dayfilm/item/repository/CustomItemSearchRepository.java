package com.rabbit.dayfilm.item.repository;

import com.rabbit.dayfilm.item.entity.ItemInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomItemSearchRepository {
    public List<ItemInfo> searchItemsByKeyword(String keyword, Pageable pageable);

    }
