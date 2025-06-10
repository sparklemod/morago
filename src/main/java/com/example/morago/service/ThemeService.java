package com.example.morago.service;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.requests.theme.ThemeRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.base.User;
import com.example.morago.repository.CategoryRepository;
import com.example.morago.repository.FileRepository;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.specification.ThemeSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository iconRepository;


    public PageResponse<ThemeResponse> getThemes(PageRequest pageRequest, String keyword, Boolean isActive, Long categoryId) {
        Specification<Theme> spec;
        Pageable pageable;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String);

        if (isAdmin) {
            // Админ: фильтры + сортировка по id
            spec = ThemeSpecifications.combineForAdmin(keyword, isActive, categoryId);
            pageable = pageRequest.toPageable();
        } else if (isAuthenticated) {
            // Авторизованный: фильтр по имени + сортировка по звонкам
            Long userId = ((User) auth.getPrincipal()).getId();
            spec = ThemeSpecifications.forAuthenticated(keyword, userId);
            pageable = pageRequest.toPageableWithoutSort(); // Сортировка в Specification
        } else {
            // Неавторизованный: сортировка по isPopular и id
            spec = ThemeSpecifications.forAnonymous();
            pageable = pageRequest.toPageableWithoutSort(); // Сортировка в Specification
        }

        Page<Theme> themePage = themeRepository.findAll(spec, pageable);

        Page<ThemeResponse> responsePage = themePage.map(theme -> {
            ThemeResponse response = new ThemeResponse();
            response.setId(theme.getId());
            response.setName(theme.getName());
            response.setIsActive(theme.getIsActive());
            response.setIconName(theme.getIcon() != null ? theme.getIcon().getOriginalTitle() : null);
            response.setCategoryName(theme.getCategory() != null ? theme.getCategory().getName() : null);
            response.setIsPopular(theme.getIsPopular());
            return response;
        });

        return new PageResponse<>(responsePage);
    }

    public ThemeResponse createTheme(ThemeRequest themeRequest) {
        Category category = categoryRepository.findById(themeRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + themeRequest.getCategoryId()));
        File icon = null;
        if (themeRequest.getIconId() != null) {
            icon = iconRepository.findById(themeRequest.getIconId())
                    .orElseThrow(() -> new EntityNotFoundException("Icon not found: " + themeRequest.getIconId()));
        }

        Theme theme = new Theme();
        theme.setName(themeRequest.getName());
        theme.setIsActive(themeRequest.getIsActive());
        theme.setIsPopular(themeRequest.getIsPopular());
        theme.setCategory(category);
        theme.setIcon(icon);
        Theme savedTheme = themeRepository.save(theme);
        return toThemeResponse(savedTheme);
    }

    public ThemeResponse getThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found: " + id));
        return toThemeResponse(theme);
    }

    public ThemeResponse updateTheme(Long id, ThemeRequest themeRequest) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found: " + id));
        Category category = categoryRepository.findById(themeRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + themeRequest.getCategoryId()));
        File icon = null;
        if (themeRequest.getIconId() != null) {
            icon = iconRepository.findById(themeRequest.getIconId())
                    .orElseThrow(() -> new EntityNotFoundException("Icon not found: " + themeRequest.getIconId()));
        }
        theme.setName(themeRequest.getName());
        theme.setIsActive(themeRequest.getIsActive());
        theme.setIsPopular(themeRequest.getIsPopular());
        theme.setCategory(category);
        theme.setIcon(icon);
        Theme updatedTheme = themeRepository.save(theme);
        return toThemeResponse(updatedTheme);
    }

    public void deleteTheme(Long id) {
        if (!themeRepository.existsById(id)) {
            throw new EntityNotFoundException("Theme not found: " + id);
        }
        themeRepository.deleteById(id);
    }

    private ThemeResponse toThemeResponse(Theme theme) {
        ThemeResponse response = new ThemeResponse();
        response.setId(theme.getId());
        response.setName(theme.getName());
        response.setIsActive(theme.getIsActive());
        response.setCategoryName(theme.getCategory() != null ? theme.getCategory().getName() : null);
        response.setIconName(theme.getIcon() != null ? theme.getIcon().getOriginalTitle() : null);
        response.setIsPopular(theme.getIsPopular());
        return response;
    }
}
