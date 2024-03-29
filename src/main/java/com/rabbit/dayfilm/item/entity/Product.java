package com.rabbit.dayfilm.item.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "PRODUCT_TABLE")
public class Product {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private LocalDate startDate;
    private LocalDate endDate;

    public void updateProductStatus() {
        this.productStatus = ProductStatus.AVAILABLE;
        this.startDate = null;
        this.endDate = null;
    }

    public void deleteProduct() {
        this.productStatus = ProductStatus.NOTUSE;
    }
}
