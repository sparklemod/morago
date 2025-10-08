package com.example.morago.repository;

import com.example.morago.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Set<Language> findAllByIdInAndIsActiveTrue(Set<Long> ids);
    List<Language> findByIsActiveTrue();
    Optional<Language> findByNameIgnoreCase(String name);
}