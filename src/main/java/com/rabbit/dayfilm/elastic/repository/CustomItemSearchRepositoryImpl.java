package com.rabbit.dayfilm.elastic.repository;

import com.rabbit.dayfilm.elastic.dto.ItemInfo;
import com.rabbit.dayfilm.elastic.repository.CustomItemSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomItemSearchRepositoryImpl implements CustomItemSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    @Override
    public List<ItemInfo> searchItemsByKeyword(String keyword, Pageable pageable) {
        Criteria criteria = Criteria.where("itemInfo.title").contains(keyword)
                .or(Criteria.where("itemInfo.storeName").contains(keyword));
        Query query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<ItemInfo> itemInfos = elasticsearchOperations.search(query, ItemInfo.class);
        return itemInfos.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
