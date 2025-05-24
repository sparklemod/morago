package com.example.morago.controller;

import com.example.morago.model.dto.ThemeResponse;
import com.example.morago.model.entity.Theme;
import com.example.morago.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public Page<ThemeResponse> getAllThemes(Pageable pageable) {
        return themeService.getAllThemes(pageable);
    }
}
