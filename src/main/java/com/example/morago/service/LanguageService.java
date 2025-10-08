package com.example.morago.service;

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

    public Set<Language> getByIds(Set<Long> ids) {
        return repository.findAllByIdIn(ids);
    }

    public List<Language> getAll() {
        return repository.findAll();
    }

    public Language getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Language not found with id: " + id));
    }

    public Language create(Language language) {
        return repository.save(language);
    }

    public Language update(Long id, Language updated) {
        Language existing = getById(id);
        existing.setName(updated.getName());
        existing.setIsActive(updated.getIsActive());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Language not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
