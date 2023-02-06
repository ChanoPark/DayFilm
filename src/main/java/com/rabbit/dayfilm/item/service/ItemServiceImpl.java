package com.rabbit.dayfilm.item.service;

import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemSerivce{

    private final ItemRepository itemRepository;

    @Override
    public void createItem(InsertItemRequestDto dto) {

        Item item = Item.builder()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .detail(dto.getDetail())
                .pricePerOne(dto.getPricePerOne())
                .pricePerFive(dto.getPricePerFive())
                .pricePerTen(dto.getPricePerTen())
                .brandName(dto.getBrandName())
                .modelName(dto.getModelName())
                .itemStatus(dto.getItemStatus())
                .purchasePrice(dto.getPurchasePrice())
                .method(dto.getMethod())
                .use_yn(Boolean.TRUE)
                .quantity(dto.getQuantity())
                .build();

        itemRepository.save(item);
    }
}
