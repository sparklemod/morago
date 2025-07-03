package com.example.morago.controller;

import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    @PreAuthorize("isAuthenticated() or permitAll()")
    public PageResponse<ThemeResponse> getThemes(
            @Valid @ModelAttribute ThemePageRequest themePageRequest,
            @AuthenticationPrincipal User user) {
        Long userId = userId = user != null ? user.getId() : null;
        return themeService.getPublicThemes(themePageRequest, userId, themePageRequest.getCategoryId());
    }

    @GetMapping("/{id}")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }
}
