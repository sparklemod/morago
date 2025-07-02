package com.example.morago.service;

import com.example.morago.model.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.model.dto.requests.translator.TranslatorUpdateRequest;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.entity.Language;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.Translator;
import com.example.morago.repository.TranslatorRepository;
import com.example.morago.repository.specification.TranslatorSpecification;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslatorService {

    private final TranslatorRepository translatorRepository;
    private final ThemeService themeService;
    private final LanguageService languageService;

    //В макете у переводчика данные вводятся после регистрации по номеру и паролю
    public Translator update(TranslatorUpdateRequest request) {
        Translator translator = findById(request.getId());

        Set<Theme> themes = new HashSet<>();
        if (!request.getThemeIds().isEmpty()) {
            themes = new HashSet<>(themeService.getByIds(request.getThemeIds()));
        }

        Set<Language> languages = new HashSet<>();
        if (!request.getLanguageIds().isEmpty()) {
            languages = new HashSet<>(languageService.getByIds(request.getLanguageIds()));
        }

        return translatorRepository.save(request.build(translator, themes, languages));
    }

    public Page<TranslatorGetResponse> searchTranslators(TranslatorGetRequest request) {
        Page<Translator> translators = translatorRepository.findAll(
            TranslatorSpecification.build(request),
            request.toPageable()
        );

        return translators.map(this::mapToDto);
    }

    public Translator findById(Long id) {
        return translatorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public TranslatorGetResponse mapToDto(Translator t) {
        return new TranslatorGetResponse(
            t.getId(),
            t.getFirstName(),
            t.getLastName(),
            t.getPhone(),
            t.getEmail(),
            t.getIsOnline(),
            t.getLevelOfKorean(),
            t.getDateOfBirth()
        );
    }
}