package com.rabbit.dayfilm.user.entity;

import com.rabbit.dayfilm.auth.Role;
import com.rabbit.dayfilm.item.entity.Like;
import com.rabbit.dayfilm.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "profile_path")
    private String profilePath;

    @Column(name = "profile_name")
    private String profileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Like> likes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    public void addLike(Like like) {
        this.likes.add(like);
        like.setUser(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setUser(this);
    }

}