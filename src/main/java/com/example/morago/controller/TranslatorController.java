package com.example.morago.controller;

import com.example.morago.controller.dto.requests.TranslatorGetRequest;
import com.example.morago.model.entity.Translator;
import com.example.morago.service.TranslatorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translators")
@RequiredArgsConstructor
public class TranslatorController {

    private final TranslatorService translatorService;

    @GetMapping
    @Operation(
        description = "<strong>Позволяет получить список переводчиков по различным параметрам.</strong>"
    )
    public ResponseEntity<Page<Translator>> getTranslators(@ModelAttribute TranslatorGetRequest request) {
        return ResponseEntity.ok(translatorService.searchTranslators(request));
    }
}
