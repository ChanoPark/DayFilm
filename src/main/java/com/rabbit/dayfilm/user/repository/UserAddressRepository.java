package com.rabbit.dayfilm.user.repository;

import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.user.dto.AddressDto;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query("SELECT ua.address FROM UserAddress ua WHERE ua.isDefault=true AND ua.user =:user")
    Optional<Address> findDefaultAddress(@Param("user") User user);

    @Query("SELECT new com.rabbit.dayfilm.user.dto.AddressDto(ua.address, ua.isDefault, ua.nickname) FROM UserAddress ua WHERE ua.user =:user")
    List<AddressDto> findAllByUser(@Param("user") User user);
}
