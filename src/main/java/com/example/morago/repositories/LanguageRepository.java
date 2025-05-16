package com.example.morago.repositories;

import com.example.morago.entity.impl.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByName(String name);
    boolean existsByName(String name);
}