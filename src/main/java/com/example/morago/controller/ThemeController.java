package com.example.morago.controller;

import com.example.morago.controller.dto.requests.theme.ThemePageRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public PageResponse<ThemeResponse> getThemes(@Valid @ModelAttribute ThemePageRequest themePageRequest) {
        return themeService.getPublicThemes(themePageRequest);
    }

    @GetMapping("/{id}")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }


}
