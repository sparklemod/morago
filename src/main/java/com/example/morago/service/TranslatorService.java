package com.example.morago.service;

import com.example.morago.controller.dto.requests.TranslatorGetRequest;
import com.example.morago.controller.dto.requests.TranslatorSpecification;
import com.example.morago.model.entity.Translator;
import com.example.morago.repository.TranslatorRepository;
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

    public Page<Translator> searchTranslators(TranslatorGetRequest request) {
        Sort sort = Sort.by(
            request.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
            request.getSortBy()
        );
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        return translatorRepository.findAll(
            TranslatorSpecification.build(request),
            pageable
        );
    }
}