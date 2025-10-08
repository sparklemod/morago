package com.example.morago.service;

import com.example.morago.model.dto.requests.LanguageRequest;
import com.example.morago.model.dto.response.LanguageResponse;
import com.example.morago.model.entity.Language;
import com.example.morago.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository repository;

    public List<LanguageResponse> getAllActive() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(lang -> new LanguageResponse(lang.getId(), lang.getName()))
                .toList();
    }

    public Set<Language> getActiveByIds(Set<Long> ids) {
        return repository.findAllByIdInAndIsActiveTrue(ids);
    }

    public LanguageResponse create(LanguageRequest request) {
        repository.findByNameIgnoreCase(request.getName())
                .ifPresent(lang -> {
                    throw new IllegalArgumentException("Language with this name already exists");
                });

        Language language = new Language();
        language.setName(request.getName().trim());
        language.setIsActive(true);
        Language saved = repository.save(language);

        return new LanguageResponse(saved.getId(), saved.getName());
    }

    public LanguageResponse update(Long id, LanguageRequest request) {
        Language existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Language not found with id: " + id));

        repository.findByNameIgnoreCase(request.getName())
                .filter(lang -> !lang.getId().equals(id))
                .ifPresent(lang -> {
                    throw new IllegalArgumentException("Language with this name already exists");
                });

        existing.setName(request.getName().trim());
        Language saved = repository.save(existing);

        return new LanguageResponse(saved.getId(), saved.getName());
    }

    public void softDelete(Long id) {
        Language language = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Language not found with id: " + id));
        language.setIsActive(false);
        repository.save(language);
    }
}