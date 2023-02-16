package com.rabbit.dayfilm.item.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name="ITEM_IMAGE_TABLE")
public class ItemImage {
    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private Integer orderNumber;

}
