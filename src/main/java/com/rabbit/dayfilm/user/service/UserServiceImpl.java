package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.user.dto.AddressCreateDto;
import com.rabbit.dayfilm.user.dto.AddressDto;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.entity.UserAddress;
import com.rabbit.dayfilm.user.repository.UserAddressRepository;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;

    @Override
    public CodeSet checkNickname(String nickname) {
        if (userRepository.countUserByNickname(nickname) > 0) return CodeSet.DUPLICATE_NICKNAME;
        else return CodeSet.OK;
    }

    @Override
    @Transactional
    public List<AddressDto> createAddress(AddressCreateDto addressCreateDto) {
        User user = userRepository.findById(addressCreateDto.getUserId()).orElseThrow(() -> new CustomException("회원이 존재하지 않습니다."));
        boolean isDefault;
        if (user.getAddresses().size() == 0) isDefault = true;
        else if (addressCreateDto.getIsDefault()) {
            UserAddress defaultAddress = null;
            for (UserAddress ua : user.getAddresses()) {
                if (ua.getIsDefault()) {
                    defaultAddress = ua;
                    break;
                }
            }
            if (defaultAddress != null) defaultAddress.changeDefault();
            isDefault = true;
        } else {
            isDefault = false;
        }


        UserAddress address = new UserAddress(user, addressCreateDto.getAddress(), isDefault);
        user.addAddress(address);
        userAddressRepository.save(address);

        List<AddressDto> response = new ArrayList<>();
        for (UserAddress userAddress : user.getAddresses()) {
            response.add(new AddressDto(userAddress.getAddress(), userAddress.getIsDefault()));
        }
        return response;
    }
}
