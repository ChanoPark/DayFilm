package com.rabbit.dayfilm.item.repository;


import com.rabbit.dayfilm.item.entity.ItemInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemElasticsearchRepository extends ElasticsearchRepository<ItemInfo, Long>, CustomItemSearchRepository{
    List<ItemInfo> findByItemInfoContatining(String name);
}
