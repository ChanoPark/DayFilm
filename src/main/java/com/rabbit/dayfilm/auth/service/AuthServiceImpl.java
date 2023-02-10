package com.rabbit.dayfilm.auth.service;

import com.rabbit.dayfilm.auth.Role;
import com.rabbit.dayfilm.auth.UserInfo;
import com.rabbit.dayfilm.auth.dto.SignReqDto;
import com.rabbit.dayfilm.auth.repository.AuthRedisRepository;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.exception.CustomException;
import com.rabbit.dayfilm.exception.FilterException;
import com.rabbit.dayfilm.item.dto.ImageInfoDto;
import com.rabbit.dayfilm.store.entity.Address;
import com.rabbit.dayfilm.store.entity.Store;
import com.rabbit.dayfilm.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final StoreRepository storeRepository;
    private final AuthRedisRepository authRedisRepository;

    @Value("${businessApi.serviceKey}")
    private String SERVICE_KEY;

    WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.create();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<UserInfo> userOpt = authRedisRepository.findById(email);
        if (userOpt.isPresent()) {
            UserInfo user = userOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(email)
                    .password(user.getPw())
                    .authorities("BASIC")
                    .build();
        } else {
            Optional<Store> storeOpt = storeRepository.findStoreByEmail(email);
            if (storeOpt.isPresent()) {
                Store store = storeOpt.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(email)
                        .password(store.getPw())
                        .authorities("BASIC")
                        .build();
            } else throw new CustomException("회원 정보가 올바르지 않습니다.");
        }
    }

    /**
     * 사업자 번호 상태 검사
     * @apiNote 공공포털에서 발급 받은 키가 동작하지 않는 상태.
     * 서버에 등록되기까지 기다려야 한다고 해서, 추후 확인 예정.
     */
    public boolean checkBusinessNumber(Long bno) {
        MultiValueMap<String, List<Long>> body = new LinkedMultiValueMap<>();
        body.add("b_no", List.of(bno));

        String uri = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + SERVICE_KEY;

        String result = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(body))
                .exchangeToMono(response -> response.bodyToMono(String.class))
                .block();

        log.info("encoding Key:{}", result);

        MultiValueMap<String, List<Long>> body2 = new LinkedMultiValueMap<>();
        body2.add("b_no", List.of(bno));

        String uri2 = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=2VqG27NBkWZE8I9ugAQ0ezpMMOaKKTCns6zTQ5gqZkhtVjdREPCQ3Grr5q9/4atMcTGUG+09fF7ERAQ/+9kYUw==";

        String result2 = webClient.post()
                .uri(uri2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(body2))
                .exchangeToMono(response -> response.bodyToMono(String.class))
                .block();

        log.info("decoding Key:{}", result2);
        return false;
    }

    /**
     * 대표자명 검사 (진위여부)
     */
    public void checkManagerName() {

    }

    @Transactional
    public void signStore(SignReqDto.SignStore request) {
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
        } else throw new FilterException(CodeSet.SIGNED_USER);

    }
}
