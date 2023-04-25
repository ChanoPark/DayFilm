package com.rabbit.dayfilm.item.entity;

import com.rabbit.dayfilm.review.entity.Review;
import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.store.entity.Store;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ITEM_TABLE")
@Getter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;


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
    private DeliveryMethod method;

    @Column(nullable = false)
    private Boolean use_yn;

    @Column(nullable = false)
    private Integer quantity;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImage> itemImages;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Like> likes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Review> reviews;


    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public void addItemImage(ItemImage itemImage) {
        this.itemImages.add(itemImage);
        itemImage.setItem(this);
    }

    public void checkQuantity(Integer quantity) {
        if(quantity <= 0) {
            this.use_yn = Boolean.FALSE;
        }
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void minusQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public void clearImages() {
        this.itemImages.clear();
    }

    public void addLike(Like like) {
        this.likes.add(like);
        like.setItem(this);
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setItem(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setItem(this);
    }

}
