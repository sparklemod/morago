package com.example.morago.repositories;

import com.example.morago.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(int phone);
}
