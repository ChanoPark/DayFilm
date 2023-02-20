package com.rabbit.dayfilm.item.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Product {
    @Id @GeneratedValue
    @Column(name = "rental_date_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private LocalDate startDate;
    private LocalDate endDate;
}
