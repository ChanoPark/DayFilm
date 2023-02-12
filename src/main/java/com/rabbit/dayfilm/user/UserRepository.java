package com.rabbit.dayfilm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(@Param("email") String email);

    @Transactional
    void deleteUserByEmail(@Param("email") String email);
}
