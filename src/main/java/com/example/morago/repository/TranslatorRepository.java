package com.example.morago.repository;

import com.example.morago.model.entity.Translator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslatorRepository
    extends JpaRepository<Translator, Long>, JpaSpecificationExecutor<Translator> {
}

