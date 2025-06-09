package com.example.morago.service;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.ThemeResponse;
import com.example.morago.model.entity.Theme;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.specification.ThemeSpecifications;
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
            Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
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
}
