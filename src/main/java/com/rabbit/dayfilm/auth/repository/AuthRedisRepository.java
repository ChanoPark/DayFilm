package com.rabbit.dayfilm.auth.repository;

import com.rabbit.dayfilm.auth.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthRedisRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findByRefreshToken(@Param("refreshToken") String refreshToken);
}
