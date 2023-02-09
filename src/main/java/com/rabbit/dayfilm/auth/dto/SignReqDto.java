package com.rabbit.dayfilm.auth.dto;

import com.rabbit.dayfilm.common.Bank;
import lombok.Getter;
@Getter
public class SignReqDto {

    @Getter
    public static class SignStore {
        private String email;
        private String pw;
        private String businessNumber;
        private String storeName;
        private Integer postalCode;
        private String address;
        private String addressDetail;
        private String registrationNumber;
        private String managerName;
        private Bank bank;
        private String accountHolder;
        private String accountNumber;

        public void changeEncodedPw(String encodedPw) {
            this.pw = encodedPw;
        }
    }
}
