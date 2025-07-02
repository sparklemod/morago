package com.example.morago.controller;

import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public PageResponse<ThemeResponse> getThemes(
            @Valid @ModelAttribute ThemePageRequest themePageRequest,
            Authentication auth) {
        // Проверка авторизации вынесена сюда
        Long userId = (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String))
                ? ((User) auth.getPrincipal()).getId()
                : null;
        return themeService.getPublicThemes(themePageRequest, userId, themePageRequest.getCategoryId());
    }

    @GetMapping("/{id}")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }


}
