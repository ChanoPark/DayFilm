package com.rabbit.dayfilm.user.entity;

import com.rabbit.dayfilm.store.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserAddress {
    public UserAddress(User user, Address address, boolean isDefault) {
        this.user = user;
        this.address = address;
        this.isDefault = isDefault;
    }
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Embedded
    private Address address;

    private Boolean isDefault;

    public void changeDefault() {
        this.isDefault = !this.isDefault;
    }
}
