package com.rabbit.dayfilm.item.service;

import com.amazonaws.util.CollectionUtils;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.dto.ImageInfoDto;
import com.rabbit.dayfilm.item.dto.InsertItemRequestDto;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.entity.ItemImage;
import com.rabbit.dayfilm.item.repository.ItemImageRepository;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemSerivce{
    private final S3UploadService s3UploadService;

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Override
    @Transactional
    public void createItem(List<MultipartFile> images, InsertItemRequestDto dto) {
        // store 에 대한 get method 필요, 그 후 item 빌더에 추가.
        try {
            List<ItemImage> itemImages = new ArrayList<>();

            if(!CollectionUtils.isNullOrEmpty(images)) {
                int count = 1;
                for(MultipartFile image : images) {
                    //개수 제한을 걸 경우, 조건문으로 예외 터트리면 됨.
                    String filename = "ex_name/" + dto.getModelName() + count;
                    ImageInfoDto imageInfoDto = s3UploadService.uploadFile(image, filename);
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(imageInfoDto.getImagePath())
                            .imageName(imageInfoDto.getImageName())
                            .orderNumber(count)
                            .build();
                    itemImages.add(itemImage);
                    count ++;
                }
                itemImageRepository.saveAll(itemImages);
            }

            Item item = Item.builder()
                    .storeName("sample")
                    .title(dto.getTitle())
                    .category(dto.getCategory())
                    .detail(dto.getDetail())
                    .pricePerOne(dto.getPricePerOne())
                    .pricePerFive(dto.getPricePerFive())
                    .pricePerTen(dto.getPricePerTen())
                    .brandName(dto.getBrandName())
                    .modelName(dto.getModelName())
                    .itemStatus(dto.getItemStatus())
                    .method(dto.getMethod())
                    .use_yn(Boolean.TRUE)
                    .quantity(dto.getQuantity())
                    .itemImages(itemImages)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            itemRepository.save(item);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("Item 생성 실패");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SelectAllItemsDto> selectAllItems(Category category, Pageable pageable) {
        return itemRepository.selectAllItems(category, pageable);
    }

}
