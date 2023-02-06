package com.rabbit.dayfilm.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private Integer pricePerOne;

    private Integer pricePerFive;

    private Integer pricePerTen;

    @Column(nullable = false)
    private String brandName;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private LocalDate purchaseDate;

    private Integer purchasePrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Method method;

    @Column(nullable = false)
    private Boolean use_yn;

    @Column(nullable = false)
    private Boolean permit_yn;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
