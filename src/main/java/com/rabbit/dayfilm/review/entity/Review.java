package com.rabbit.dayfilm.review.entity;

import com.rabbit.dayfilm.item.entity.Item;
import com.rabbit.dayfilm.user.User;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REVIEW_TABLE")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer star;
}
