package com.example.morago.controller.adminController;

import com.example.morago.model.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.service.TranslatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/translators")
@RequiredArgsConstructor
public class AdminTranslatorController {

    private final TranslatorService translatorService;

    @GetMapping
    @Operation(
            description = "<strong>Get a list of translators by request body parameters</strong>"
    )
    public ResponseEntity<Page<TranslatorGetResponse>> getTranslators(@ModelAttribute TranslatorGetRequest request) {
        return ResponseEntity.ok(translatorService.searchTranslators(request));
    }

    @GetMapping("/{id}")
    @Operation(
            description = "<strong>Get translator by ID</strong>"
    )
    public ResponseEntity<TranslatorGetResponse> getTranslatorById(
            @PathVariable("id")
            @Parameter(description = "Translator ID", example = "2")
            Long id) {
        return ResponseEntity.ok(translatorService.mapToDto(translatorService.findById(id)));
    }
}
