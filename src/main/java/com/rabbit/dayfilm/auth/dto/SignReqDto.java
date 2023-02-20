package com.rabbit.dayfilm.auth.dto;

import com.rabbit.dayfilm.common.Bank;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SignReqDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignStore {

        @ApiModelProperty(value="아이디(이메일)", example="test@gmail.com", required = true)
        private String email;

        @ApiModelProperty(value="비밀번호", example="testpw123", required = true)
        private String pw;

        @ApiModelProperty(value="사업자등록번호", example="123-45-67890", required = true)
        private String businessNumber;

        @ApiModelProperty(value="상호명(가게 이름) == 닉네임", example="테스트 가게1", required = true)
        private String storeName;

        @ApiModelProperty(value="우편 번호", example="12345", required = true)
        private Integer postalCode;

        @ApiModelProperty(value="주소", example="안산시 단원구", required = true)
        private String address;

        @ApiModelProperty(value="상세 주소", example="원선 1로", required = true)
        private String addressDetail;

        @ApiModelProperty(value="통신판매업신고번호", example="2022-어쩌구-저쩌구", required = true)
        private String registrationNumber;

        @ApiModelProperty(value="담당자 명", example="담당자이름", required = true)
        private String managerName;

        @ApiModelProperty(value="은행(우선 IBK로 고정해주세요!)", example="IBK", required = true)
        private Bank bank;

        @ApiModelProperty(value="예금주", example="예금주", required = true)
        private String accountHolder;

        @ApiModelProperty(value="계좌번호(-제외)", example="1234567", required = true)
        private String accountNumber;

        public void changeEncodedPw(String encodedPw) {
            this.pw = encodedPw;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUser {
        @ApiModelProperty(value="아이디(이메일)", example="test@gmail.com", required = true)
        private String email;

        @ApiModelProperty(value="비밀번호", example="testpw123", required = true)
        private String pw;

        @ApiModelProperty(value="닉네임", example="테스트 회원1", required = true)
        private String nickname;

        public void changeEncodedPw(String encodedPw) {
            this.pw = encodedPw;
        }
    }
}
