package com.rabbit.dayfilm.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="ITEM_IMAGE_TABLE")
public class ItemImage {
    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private Integer orderNumber;

}
