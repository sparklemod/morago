package com.example.morago.service;

import com.example.morago.controller.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.controller.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.entity.Translator;
import com.example.morago.repository.TranslatorRepository;
import com.example.morago.specification.TranslatorSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslatorService {

    private final TranslatorRepository translatorRepository;

    public Page<TranslatorGetResponse> searchTranslators(TranslatorGetRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<Translator> translators = translatorRepository.findAll(
            TranslatorSpecification.build(request),
            pageable
        );

        return translators.map(this::mapToDto);
    }

    public Translator searchUserById(Long id) {
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