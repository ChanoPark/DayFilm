package com.rabbit.dayfilm.basket.service;

import com.rabbit.dayfilm.basket.dto.BasketCond;
import com.rabbit.dayfilm.basket.dto.BasketCreateDto;
import com.rabbit.dayfilm.basket.dto.BasketReqDto;
import com.rabbit.dayfilm.basket.dto.BasketResDto;
import com.rabbit.dayfilm.basket.entity.Basket;
import com.rabbit.dayfilm.basket.repository.BasketRepository;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.item.entity.ProductStatus;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createBasket(BasketCreateDto request) {
        LocalDateTime started = request.getStarted();
        LocalDateTime ended = request.getEnded();
        if (started.isBefore(LocalDateTime.now()) || ended.isBefore(LocalDateTime.now()) || started.isAfter(ended))
            throw new CustomException("대여 시간이 올바르지 않습니다.");

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new CustomException("상품이 존재하지 않습니다."));
        Optional<Basket> rentalProductOpt = basketRepository.findBasketByUserAndProduct(user, product);

        if (rentalProductOpt.isEmpty()) {
            if (product.getProductStatus().equals(ProductStatus.AVAILABLE)) {
                Basket basket = Basket.builder()
                        .user(user)
                        .product(product)
                        .started(started)
                        .ended(ended)
                        .method(request.getMethod())
                        .build();
                basketRepository.save(basket);
            } else throw new CustomException("렌탈이 불가능한 상품입니다.");
        } else throw new CustomException("장바구니에 존재하는 상품입니다.");
    }

    @Override
    public List<BasketResDto> findAllBasket(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));
        Page<BasketResDto.BasketQueryDto> basketList = basketRepository.findBasketAll(new BasketCond(user, pageable));

        List<BasketResDto> result = new ArrayList<>();
        for (BasketResDto.BasketQueryDto basket : basketList) {
            int price;

            long rentDays = ChronoUnit.DAYS.between(basket.getStarted(), basket.getEnded());
            if (rentDays >= 5 && rentDays < 10) price = basket.getPricePerFive();
            else if (rentDays >= 10) price = basket.getPricePerTen();
            else price = basket.getPricePerOne();

            result.add(
                    BasketResDto.builder()
                            .basketId(basket.getBasketId())
                            .imagePath(basket.getImagePath())
                            .title(basket.getTitle())
                            .method(basket.getMethod())
                            .started(basket.getStarted())
                            .ended(basket.getEnded())
                            .price(price)
                            .build()
            );
        }
        return result;
    }

    @Override
    public void deleteBaskets(BasketReqDto.DeleteBaskets request) {
        List<Long> ids = request.getBasketIds();
        if (ids.size() == 0) throw new CustomException("삭제할 장바구니가 없습니다.");
        else {
            try {
                basketRepository.deleteAllById(ids);
            } catch (EmptyResultDataAccessException e) {
                throw new CustomException("장바구니 번호가 올바르지 않습니다.");
            }
        }
    }
}