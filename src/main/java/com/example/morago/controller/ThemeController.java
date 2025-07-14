package com.example.morago.controller;

import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.requests.theme.ThemeRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping("/themes")
    @Operation(description = "Get public list of themes")
    @PreAuthorize("isAuthenticated() or permitAll()")
    public PageResponse<ThemeResponse> getThemes(
            @Valid @ModelAttribute ThemePageRequest themePageRequest,
            @AuthenticationPrincipal User user) {
        Long userId = user != null ? user.getId() : null;
        return themeService.getPublicThemes(themePageRequest, userId, themePageRequest.getCategoryId());
    }

    @GetMapping("/themes/{id}")
    @Operation(description = "Get theme [ALL]")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }

    //ADMIN
    @GetMapping("/admin/themes")
    @Operation(description = "Get themes [ADMIN]")
    public PageResponse<ThemeResponse> getThemes(@Valid @ModelAttribute ThemePageRequest themePageRequest) {
        return themeService.getAdminThemes(themePageRequest);
    }

    @PostMapping("/admin/themes")
    @Operation(description = "Create theme [ADMIN]")
    @ResponseStatus(HttpStatus.CREATED)
    public ThemeResponse createTheme(@Valid @RequestBody ThemeRequest themeRequest) {
        return themeService.createTheme(themeRequest);
    }

    @PutMapping("/admin/themes/{id}")
    @Operation(description = "Update theme [ADMIN]")
    public ThemeResponse updateTheme(@PathVariable Long id, @Valid @RequestBody ThemeRequest request) {
        return themeService.updateTheme(id, request);
    }

    @PostMapping("/admin/themes/{id}/icon")
    @Operation(description = "Update theme icon [ADMIN]")
    public ThemeResponse updateThemeIcon(@PathVariable Long id, @RequestParam("icon") MultipartFile iconFile) {
        return themeService.updateThemeIcon(id, iconFile);
    }
    @DeleteMapping("/admin/themes/{id}")
    @Operation(description = "Delete theme [ADMIN]")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }
}
