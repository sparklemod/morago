package com.example.morago.service;

import com.example.morago.model.entity.Language;
import com.example.morago.repository.LanguageRepository;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository repository;

    public Collection<Language> getByIds(Set<Long> ids) {
        return repository.findAllByIdIn(ids);
    }
}
