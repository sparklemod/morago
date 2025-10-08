package com.example.morago.controller;

import com.example.morago.model.dto.response.LanguageResponse;
import com.example.morago.model.entity.Language;
import com.example.morago.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService service;

    @GetMapping
    public List<LanguageResponse> getAllActiveLanguages() {
        return service.getAllActive();
    }

    @GetMapping("/by-ids")
    public List<LanguageResponse> getLanguagesByIds(@RequestParam Set<Long> ids) {
        Set<Language> languages = service.getActiveByIds(ids);
        return languages.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private LanguageResponse convertToResponse(Language language) {
        return new LanguageResponse(
                language.getId(),
                language.getName()
        );
    }
}
