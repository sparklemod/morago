package com.example.morago.controller.adminController;

import com.example.morago.controller.dto.requests.theme.ThemePageRequest;
import com.example.morago.controller.dto.requests.theme.ThemeRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{id}")
    public ThemeResponse getTheme(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }


    @PutMapping("/{id}")
    public ThemeResponse updateTheme(@PathVariable Long id, @Valid @RequestBody ThemeRequest request) {
        return themeService.updateTheme(id, request);
    }

    @PostMapping("/{id}/icon")
    public ThemeResponse updateThemeIcon(@PathVariable Long id, @RequestParam("icon") MultipartFile iconFile) {
        return themeService.updateThemeIcon(id, iconFile);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
    }

}
