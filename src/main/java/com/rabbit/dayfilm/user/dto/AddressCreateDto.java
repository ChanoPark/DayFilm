package com.rabbit.dayfilm.user.dto;

import com.rabbit.dayfilm.store.entity.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class AddressCreateDto {
    public AddressCreateDto(Long userId, Integer postalCode, String address, String addressDetail, Boolean isDefault, String nickname) {
        this.userId = userId;
        this.address = new Address(postalCode, address, addressDetail);
        this.isDefault = isDefault;
        this.nickname = nickname;
    }
    @ApiModelProperty(value="회원 번호", example="1", required = true)
    private Long userId;
    @ApiModelProperty(value="주소", example="주소주소", required = true)
    private Address address;
    @ApiModelProperty(value="기본 주소 여부(default)", example="true", required = true)
    private Boolean isDefault;
    @ApiModelProperty(value="주소지 별칭", example="우리집", required = true)
    private String nickname;
}
