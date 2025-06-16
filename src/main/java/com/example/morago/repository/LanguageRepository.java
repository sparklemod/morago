package com.example.morago.repository;

import com.example.morago.model.entity.Language;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findAllByIdIn(Collection<Long> ids);
}