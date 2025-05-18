package com.example.morago.repository;

import com.example.morago.model.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(int phone);
    boolean existsByPhone(int phone);
}
