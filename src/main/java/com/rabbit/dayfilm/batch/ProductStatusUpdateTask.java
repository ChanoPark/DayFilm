package com.rabbit.dayfilm.batch;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.item.entity.Product;
import com.rabbit.dayfilm.item.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductStatusUpdateTask {

    private final ProductRepository productRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateProductStatus() {
        try {
            LocalDate currentDate = LocalDate.now();
            List<Product> findProducts = productRepository.findProductsByEndDateLessThan(currentDate);
            for (Product product : findProducts) {
                product.updateProductStatus();
                productRepository.save(product);
            }
        } catch (RuntimeException e) {
            log.error("제품 상태 변경 배치 오류 : {}", e.getMessage());
            throw new CustomException("제품 상태 변경 배치 오류 발생");
        }

    }

}
