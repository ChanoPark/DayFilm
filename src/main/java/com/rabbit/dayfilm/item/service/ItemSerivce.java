package com.rabbit.dayfilm.item.service;

import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemSerivce {
    public void createItem(List<MultipartFile> images, InsertItemRequestDto dto);
    public List<SelectAllItemsDto> selectAllItems();
}
