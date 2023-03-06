package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.user.dto.AddressCreateDto;
import com.rabbit.dayfilm.user.dto.AddressDto;

import java.util.List;

public interface UserService {
    CodeSet checkNickname(String nickname);

    List<AddressDto> createAddress(AddressCreateDto addressCreateDto);

    List<AddressDto> getAddresses(Long userId);

    void deleteAddress(Long addressId);
}
