package com.rabbit.dayfilm.item.service;

import com.amazonaws.util.CollectionUtils;
import com.rabbit.dayfilm.common.util.CopyUtil;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.dto.*;
import com.rabbit.dayfilm.item.entity.*;
import com.rabbit.dayfilm.item.repository.*;
import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.store.entity.Store;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final S3UploadService s3UploadService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void createItem(List<MultipartFile> images, List<MultipartFile> infoImages, CreateItemRequest dto) {

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
                    .address(
                        Address.builder()
                                .address(dto.getAddress())
                                .addressDetail(dto.getAddressDetail())
                                .postalCode(dto.getPostalCode())
                                .build())
                    .itemImages(new ArrayList<>())
                    .products(new ArrayList<>())
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build(); //아이템 엔티티 먼저 생성

            item.checkQuantity(item.getQuantity()); // 시작 재고가 0이 아닌지 체크

            for (int i = 0; i < item.getQuantity(); i++) {
                Product product = Product.builder()
                        .productStatus(ProductStatus.AVAILABLE)
                        .build();
                item.addProduct(product);
            }

            if (!CollectionUtils.isNullOrEmpty(images)) {
                int count = 1;
                for (MultipartFile image : images) {
                    //개수 제한을 걸 경우, 조건문으로 예외 터트리면 됨.
                    String filename = store.getStoreName() + "/" + dto.getModelName() + "/product/" + count;
                    ImageInfoDto imageInfoDto = s3UploadService.uploadFile(image, filename);
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(imageInfoDto.getImagePath())
                            .imageName(imageInfoDto.getImageName())
                            .orderNumber(count)
                            .imageType(ImageType.PRODUCT)
                            .build();
                    item.addItemImage(itemImage);
                    count++;
                }
            }

            if (!CollectionUtils.isNullOrEmpty(infoImages)) {
                int count = 1;
                for (MultipartFile image : infoImages) {
                    //개수 제한을 걸 경우, 조건문으로 예외 터트리면 됨.
                    String filename = store.getStoreName() + "/" + dto.getModelName() + "/info/" + count;
                    ImageInfoDto imageInfoDto = s3UploadService.uploadFile(image, filename);
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(imageInfoDto.getImagePath())
                            .imageName(imageInfoDto.getImageName())
                            .orderNumber(count)
                            .imageType(ImageType.INFO)
                            .build();
                    item.addItemImage(itemImage);
                    count++;
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
    @Cacheable(value = "item-detail", key="#id", cacheManager = "cacheManager", unless="#result == null")
    public SelectDetailDto selectDetailItem(Long id) {
        return itemRepository.selectItem(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SelectStoreDto> selectWriteItems(Category category, Long storeId) {
        return itemRepository.selectWriteItems(category, storeId);
    }

    @Override
    @Transactional
    @CachePut(value = "item-detail", key="#itemId", cacheManager = "cacheManager", unless="#result == null")
    public void modifyItem(Long itemId, List<MultipartFile> images, ModifyItemRequestDto dto) {
        try {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new CustomException("해당 번호 아이템이 존재하지 않습니다."));

            // DTO 객체의 null이 아닌 속성을 기존 Item 객체에 복사.
            BeanUtils.copyProperties(dto, item, CopyUtil.getNullPropertyNames(dto));

            //기존 s3 image 삭제
            List<ItemImage> itemImages = item.getItemImages();
            for (ItemImage itemImage : itemImages) {
                s3UploadService.deleteFile(itemImage.getImageName());
            }
            //이미지 비우기
            item.clearImages();
            itemImageRepository.deleteByItemId(item.getId());

            //이미지 새롭게 업로드
            String storeName = item.getStoreName();
            if (!CollectionUtils.isNullOrEmpty(images)) {
                int count = 1;
                for (MultipartFile image : images) {
                    //개수 제한을 걸 경우, 조건문으로 예외 터트리면 됨.
                    String filename = storeName + "/" + dto.getModelName() + count;
                    ImageInfoDto imageInfoDto = s3UploadService.uploadFile(image, filename);
                    ItemImage itemImage = ItemImage.builder()
                            .imagePath(imageInfoDto.getImagePath())
                            .imageName(imageInfoDto.getImageName())
                            .orderNumber(count)
                            .build();
                    item.addItemImage(itemImage);
                    count++;
                }
            }

            itemRepository.save(item);

        } catch (IOException e) {
            log.info("error message : {}", e.getMessage());
            throw new CustomException("Item 수정 실패");
        }


    }

    @Override
    @Transactional(readOnly = true)
    public Page<SelectAllItemsDto> selectLikeItems(Category category, Long userId, Pageable pageable) {
        return itemRepository.selectLikeItems(category, userId, pageable);
    }

    @Override
    @Transactional
    public void likeItem(LikeItemRequestDto dto) {

        User findUser = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("해당 번호 유저가 존재하지 않습니다."));

        Item findItem = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new CustomException("해당 번호 아이템이 존재하지 않습니다."));

        //좋아요 객체 생성
        Like like = new Like();

        //연관관계 추가 : 변경감지로 인해 자동 업데이트
        findUser.addLike(like);
        findItem.addLike(like);

        likeRepository.save(like);
    }

}
