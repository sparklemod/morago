package com.example.morago.repository;

import com.example.morago.model.entity.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByName(String name);

    boolean existsByName(String name);

    Page<Theme> findAll(Pageable pageable);
}
