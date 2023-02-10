package com.rabbit.dayfilm.auth.repository;

import com.rabbit.dayfilm.auth.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface AuthRedisRepository extends CrudRepository<UserInfo, String> {
}
