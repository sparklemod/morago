package com.example.morago.repository;

import com.example.morago.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Set<Language> findAllByIdIn(Set<Long> ids);
}