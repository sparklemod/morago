package com.example.morago.service;

import com.example.morago.model.entity.Language;
import com.example.morago.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository repository;

    public Set<Language> getByIds(Set<Long> ids) {
        return repository.findAllByIdIn(ids);
    }
}
