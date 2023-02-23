package com.rabbit.dayfilm.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rabbit.dayfilm.auth.AuthUtil;
import com.rabbit.dayfilm.auth.Role;
import com.rabbit.dayfilm.auth.UserInfo;
import com.rabbit.dayfilm.auth.dto.LoginInfo;
import com.rabbit.dayfilm.auth.dto.SignReqDto;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.FilterException;
import com.rabbit.dayfilm.item.dto.ImageInfoDto;
import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.store.entity.Store;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import com.rabbit.dayfilm.user.entity.User;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final AuthRedisRepository authRedisRepository;

    @Value("${secure.jwt.secretKey}")
    private String SECRET_KEY;
    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.create();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserInfo> userOpt = authRedisRepository.findById(email);
        UserInfo userInfo = userOpt.orElseThrow(() -> new FilterException(CodeSet.INVALID_USER));
        return org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password(userInfo.getPw())
                .authorities("BASIC")
                .build();
    }

    /**
     * 가게(Store) 회원 가입
     * @param request
     * @param refreshToken
     */
    @Transactional
    public void signStore(SignReqDto.SignStore request, String refreshToken) {
        Optional<UserInfo> userOpt = authRedisRepository.findById(request.getEmail());

        if (userOpt.isEmpty()) {
            ImageInfoDto imageInfoDto = new ImageInfoDto("", ""); //S3 완성 후 연결 예정 or 제거 예정

            Store store = Store.builder()
                    .email(request.getEmail())
                    .pw(request.getPw())
                    .role(Role.STORE)
                    .businessNumber(request.getBusinessNumber())
                    .storeName(request.getStoreName())
                    .address(
                            Address.builder()
                                    .address(request.getAddress())
                                    .addressDetail(request.getAddressDetail())
                                    .postalCode(request.getPostalCode())
                                    .build()
                    )
                    .managerName(request.getManagerName())
                    .bank(request.getBank())
                    .accountHolder(request.getAccountHolder())
                    .accountNumber(request.getAccountNumber())
                    .pictureName(imageInfoDto.getImageName())
                    .picturePath(imageInfoDto.getImagePath())
                    .build();

            storeRepository.save(store);
            authRedisRepository.save(
                    UserInfo.builder()
                            .email(store.getEmail())
                            .pw(store.getPw())
                            .refreshToken(refreshToken)
                            .nickname(request.getStoreName())
                            .role(Role.STORE)
                            .build()
            );
        } else throw new FilterException(CodeSet.SIGNED_USER);
    }

    /**
     * 일반 회원 가입
     * @param request
     * @param refreshToken
     */
    @Transactional
    public void signUser(SignReqDto.SignUser request, String refreshToken) {
        Optional<UserInfo> userOpt = authRedisRepository.findById(request.getEmail());

        if (userOpt.isEmpty()) {
            User user = User.builder()
                    .email(request.getEmail())
                    .pw(request.getPw())
                    .nickname(request.getNickname())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
            authRedisRepository.save(
                    UserInfo.builder()
                            .email(user.getEmail())
                            .pw(user.getPw())
                            .refreshToken(refreshToken)
                            .nickname(user.getNickname())
                            .role(Role.USER)
                            .build()
            );
        } else throw new FilterException(CodeSet.SIGNED_USER);
    }

    /**
     * 토큰의 payload에 있는 로그인 정보 조회
     * @param token
     * @return LoginInfo
     */
    public LoginInfo getLoginInfoByToken(String token) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        DecodedJWT decodedToken = JWT.require(ALGORITHM).build().verify(token);
        String email = decodedToken.getSubject();
        String encryptedPw = decodedToken.getClaim("code").asString();
        String privateKey = decodedToken.getClaim("secret_key").asString();

        String decryptedPw = AuthUtil.decrypt(encryptedPw, privateKey);
        return new LoginInfo(email, decryptedPw);
    }

    /**
     * 토큰 내부에 있는 비밀번호 복호화
     */
    public String decryptPwInToken(String token, String encryptedPw) {
        final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

        DecodedJWT decodedToken = JWT.require(ALGORITHM).build().verify(token);
        String secretKey = decodedToken.getClaim("secret_key").asString();
        return AuthUtil.decrypt(encryptedPw, secretKey);
    }
}
