package com.rabbit.dayfilm.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue
    @Column(name = "rental_date_id")
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
}
