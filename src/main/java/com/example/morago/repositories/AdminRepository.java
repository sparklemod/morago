package com.example.morago.repositories;

import com.example.morago.entity.impl.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByPhone(int phone);
    boolean existsByPhone(int phone);
}
