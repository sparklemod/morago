package com.example.morago.controller;

import com.example.morago.config.security.userDetails.CustomUserDetails;
import com.example.morago.model.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.model.dto.requests.translator.TranslatorUpdateRequest;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.service.TranslatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Tag(name = "TranslationController")
public class TranslatorController {

    private final TranslatorService translatorService;

    //ADMIN
    @GetMapping("/admin/translators")
    @Operation(description = "Get a list of translators by request body parameters")
    public ResponseEntity<Page<TranslatorGetResponse>> getTranslators(@ModelAttribute TranslatorGetRequest request) {
        return ResponseEntity.ok(translatorService.searchTranslators(request));
    }

    @GetMapping("/admin/translators/{id}")
    @Operation(description = "Get translator by ID")
    public ResponseEntity<TranslatorGetResponse> getTranslatorById(

            @PathVariable("id")
            @Parameter(description = "Translator ID", example = "2")
            Long id) {
        return ResponseEntity.ok(translatorService.mapToDto(translatorService.findById(id)));
    }

    //TRANSLATOR
    @PutMapping("/translator")
    @Operation(description = "Update translator")
    public ResponseEntity<TranslatorGetResponse> updateTranslator(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody TranslatorUpdateRequest request) {
        return ResponseEntity.ok(translatorService.mapToDto(translatorService.update(request)));
    }
}
