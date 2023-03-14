package com.rabbit.dayfilm.user.dto;

import com.rabbit.dayfilm.store.entity.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressDto {
    @ApiModelProperty(value="주소", example="주소주소", required = true)
    private Address address;
    @ApiModelProperty(value="기본 주소 여부(default)", example="true", required = true)
    private Boolean isDefault;
    @ApiModelProperty(value="주소지 별칭", example="우리집", required = true)
    private String nickname;
}
