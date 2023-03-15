package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.user.dto.AddressCreateDto;
import com.rabbit.dayfilm.user.dto.AddressDto;

import java.util.List;

public interface UserAddressService {
    List<AddressDto> createAddress(AddressCreateDto addressCreateDto);
    List<AddressDto> getAddresses(Long userId);
    void deleteAddress(Long addressId);
}
