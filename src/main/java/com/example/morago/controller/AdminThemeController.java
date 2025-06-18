package com.example.morago.controller;

import com.example.morago.controller.dto.requests.theme.ThemePageRequest;
import com.example.morago.controller.dto.requests.theme.ThemeRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/themes")
@RequiredArgsConstructor
public class AdminThemeController {
    private final ThemeService themeService;

    @GetMapping
    public PageResponse<ThemeResponse> getThemes(@Valid @ModelAttribute ThemePageRequest themePageRequest) {
        return themeService.getAdminThemes(themePageRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThemeResponse createTheme(@Valid @RequestBody ThemeRequest themeRequest) {
        return themeService.createTheme(themeRequest);
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
