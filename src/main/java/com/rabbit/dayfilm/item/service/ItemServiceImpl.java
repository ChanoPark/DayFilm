package com.rabbit.dayfilm.item.service;

import com.amazonaws.util.CollectionUtils;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.dto.*;
import com.rabbit.dayfilm.item.entity.Category;
import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.item.entity.ItemImage;
import com.rabbit.dayfilm.item.repository.ItemImageRepository;
import com.rabbit.dayfilm.item.repository.ItemRepository;
import com.rabbit.dayfilm.store.entity.Store;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemSerivce{
    private final S3UploadService s3UploadService;
    private final StoreRepository storeRepository;

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Override
    @Transactional
    public void createItem(List<MultipartFile> images, InsertItemRequestDto dto) {

        try {
            Store store = storeRepository.findById(dto.getStoreId())
                    .orElseThrow(() -> new CustomException("해당 번호에 해당하는 가게를 찾을 수 없습니다."));

            Item item = Item.builder()
                    .store(store)
                    .storeName(store.getStoreName())
                    .title(dto.getTitle())
                    .category(dto.getCategory())
                    .detail(dto.getDetail())
                    .pricePerOne(dto.getPricePerOne())
                    .pricePerFive(dto.getPricePerFive())
                    .pricePerTen(dto.getPricePerTen())
                    .brandName(dto.getBrandName())
                    .modelName(dto.getModelName())
                    .method(dto.getMethod())
                    .use_yn(Boolean.TRUE)
                    .quantity(dto.getQuantity())
                    .itemImages(new ArrayList<>())
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build(); //아이템 엔티티 먼저 생성

            item.checkQuantity(item.getQuantity()); // 시작 재고가 0이 아닌지 체크

            if(!CollectionUtils.isNullOrEmpty(images)) {
                int count = 1;
                for(MultipartFile image : images) {
                    //개수 제한을 걸 경우, 조건문으로 예외 터트리면 됨.
                    String filename = store.getStoreName() + "/" + dto.getModelName() + count;
                    ImageInfoDto imageInfoDto = s3UploadService.uploadFile(image, filename);
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(imageInfoDto.getImagePath())
                            .imageName(imageInfoDto.getImageName())
                            .orderNumber(count)
                            .build();
                    item.addItemImage(itemImage);
                    count ++;
                }
            }

            itemRepository.save(item);
            itemImageRepository.saveAll(item.getItemImages());

        } catch (IOException e) {
            log.info("error message : {}", e.getMessage());
            throw new CustomException("Item 생성 실패");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SelectAllItemsDto> selectAllItems(Category category, Pageable pageable) {
        return itemRepository.selectAllItems(category, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SelectDetailItemDto selectDetailItem(Long id) {
        return itemRepository.selectItem(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SelectAllItemsDto> selectWriteItems(Long storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException("해당 하는 id의 가게를 찾을 수 없습니다."));
        return itemRepository.selectWriteItems(store, pageable);
    }

    @Override
    @Transactional
    public void modifyItem(Long itemId, List<MultipartFile> images, ModifyItemDto dto) {
        try{
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new CustomException("해당 번호 아이템이 존재하지 않습니다."));

            // DTO 객체의 null이 아닌 속성을 기존 Item 객체에 복사.
            BeanUtils.copyProperties(dto, item, getNullPropertyNames(dto));

            //기존 s3 image 삭제
            List<ItemImage> itemImages = item.getItemImages();
            for (ItemImage itemImage : itemImages) {
                s3UploadService.deleteFile(itemImage.getImageName());
            }
            //이미지 비우기
            item.clearImages();

            //이미지 새롭게 업로드
            String storeName = item.getStoreName();
            if(!CollectionUtils.isNullOrEmpty(images)) {
                int count = 1;
                for(MultipartFile image : images) {
                    //개수 제한을 걸 경우, 조건문으로 예외 터트리면 됨.
                    String filename = storeName + "/" + dto.getModelName() + count;
                    ImageInfoDto imageInfoDto = s3UploadService.uploadFile(image, filename);
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(imageInfoDto.getImagePath())
                            .imageName(imageInfoDto.getImageName())
                            .orderNumber(count)
                            .build();
                    item.addItemImage(itemImage);
                    count ++;
                }
            }

            itemRepository.save(item);

        } catch (IOException e) {
            log.info("error message : {}", e.getMessage());
            throw new CustomException("Item 수정 실패");
        }



    }

    private static String[] getNullPropertyNames(Object source) {
        // 소스 객체에 대한 BeanWrapper 객체 생성
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        // null 값을 가진 속성의 이름을 저장하기 위한 집합 만들기
        Set<String> emptyNames = new HashSet<>();

        // 소스 객체의 모든 속성을 반복
        // 값이 null이면 속성 이름을 집합에 추가
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}
