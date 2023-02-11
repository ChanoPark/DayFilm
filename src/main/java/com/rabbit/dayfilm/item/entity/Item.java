package com.rabbit.dayfilm.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;

    @Column(nullable = false)
    private String storeName; //리스트 가져올 때 가게 이름이 필요한데, join 쿼리를 사용할 때 리소스낭비가 심할 것으로 예상되서 name만 중복


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

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<ItemImage> itemImages;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
