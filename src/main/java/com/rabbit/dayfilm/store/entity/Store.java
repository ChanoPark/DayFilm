package com.rabbit.dayfilm.store.entity;

import com.rabbit.dayfilm.auth.Role;
import com.rabbit.dayfilm.common.Bank;
import com.rabbit.dayfilm.item.entity.Item;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="store")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "business_number", nullable = false, unique = true)
    private String businessNumber;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "bank", nullable = false)
    @Enumerated(EnumType.STRING)
    private Bank bank;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "picture_path", nullable = false)
    private String picturePath;

    @Column(name = "picture_name", nullable = false)
    private String pictureName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store", cascade = CascadeType.PERSIST)
    private List<Item> items;
}