package com.rabbit.dayfilm.user.repository;

import com.rabbit.dayfilm.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
