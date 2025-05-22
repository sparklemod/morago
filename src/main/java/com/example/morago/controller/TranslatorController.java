package com.example.morago.controller;

import com.example.morago.controller.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.controller.dto.response.translator.TranslatorGetResponse;
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
    public ResponseEntity<Page<TranslatorGetResponse>> getTranslators(@ModelAttribute TranslatorGetRequest request) {
        return ResponseEntity.ok(translatorService.searchTranslators(request));
    }
}
