package com.rabbit.dayfilm.elastic.repository;


import com.rabbit.dayfilm.elastic.dto.ItemInfo;
import com.rabbit.dayfilm.elastic.repository.CustomItemSearchRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemElasticsearchRepository extends ElasticsearchRepository<ItemInfo, Long>, CustomItemSearchRepository {
    List<ItemInfo> findByItemInfoContatining(String name);
}
