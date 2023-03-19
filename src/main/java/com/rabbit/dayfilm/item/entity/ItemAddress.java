package com.rabbit.dayfilm.item.entity;

import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ItemAddress {
    public ItemAddress(Item item, Address address, boolean isDefault, String nickname) {
        this.item = item;
        this.address = address;
        this.isDefault = isDefault;
        this.nickname = nickname;
    }
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Embedded
    private Address address;

    private Boolean isDefault;

    private String nickname;

    public void changeDefault() {
        this.isDefault = !this.isDefault;
    }
}
