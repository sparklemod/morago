package com.example.morago.controller;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.requests.theme.ThemeRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public PageResponse<ThemeResponse> getThemes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Long categoryId,
            @Valid @ModelAttribute PageRequest pageRequest) {
        return themeService.getThemes(pageRequest, keyword, isActive, categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThemeResponse createTheme(@Valid @ModelAttribute ThemeRequest themeRequest) {
        return themeService.createTheme(themeRequest);
    }

    @GetMapping("/{id}")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return  themeService.getThemeById(id);
    }

    @PutMapping("/{id}")
    public ThemeResponse updateTheme(@PathVariable Long id, @Valid @RequestBody ThemeRequest request) {
        return themeService.updateTheme(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }

}
