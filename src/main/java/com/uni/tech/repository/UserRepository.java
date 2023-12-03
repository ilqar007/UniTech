package com.uni.tech.repository;

import com.uni.tech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPin(String pin);

    boolean existsByPin(String pin);
}