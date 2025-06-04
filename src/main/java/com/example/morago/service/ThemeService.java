package com.example.morago.service;

import com.example.morago.model.dto.ThemeResponse;
import com.example.morago.model.entity.Theme;
import com.example.morago.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public Page<ThemeResponse> getAllThemes(Pageable pageable) {
        Page<Theme> themePage = themeRepository.findAll(pageable);
        return themePage.map(ThemeResponse::new);
    }
}
