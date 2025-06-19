package com.example.morago.service;

import com.example.morago.controller.dto.requests.theme.ThemePageRequest;
import com.example.morago.controller.dto.requests.theme.ThemeRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.base.User;
import com.example.morago.repository.CategoryRepository;
import com.example.morago.repository.FileRepository;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.specification.ThemeSpecifications;
import com.example.morago.service.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository iconRepository;
    private final FileService fileService;

    // Получение тем по переводчику
    public Collection<Theme> getByIds(Set<Long> ids) {
        return themeRepository.findAllByIdIn(ids);
    }

    // Публичный список тем
    public PageResponse<ThemeResponse> getPublicThemes(ThemePageRequest themePageRequest) {
        Specification<Theme> spec;
        Pageable pageable;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String);

        if (isAuthenticated) {
            // Авторизованный: фильтр по имени + сортировка по звонкам
            Long userId = ((User) auth.getPrincipal()).getId();
            spec = ThemeSpecifications.forAuthenticated(themePageRequest.getKeyword(), userId);
            pageable = themePageRequest.toPageableWithoutSort(); // Сортировка в Specification
        } else {
            // Неавторизованный: сортировка по isPopular и id
            spec = ThemeSpecifications.forAnonymous();
            pageable = themePageRequest.toPageableWithoutSort(); // Сортировка в Specification
        }
        //Список тем для Админа
        Page<Theme> page = themeRepository.findAll(spec, pageable);
        return mapToPageResponse(page);
    }

    //Список тем для Админа
    public PageResponse<ThemeResponse> getAdminThemes(ThemePageRequest themePageRequest) {
        Specification<Theme> spec = ThemeSpecifications.combineForAdmin(
                themePageRequest.getKeyword(), themePageRequest.getIsActive(), themePageRequest.getCategoryId());
        Pageable pageable = themePageRequest.toPageable();

        Page<Theme> themePage = themeRepository.findAll(spec, pageable);
        return mapToPageResponse(themePage);
    }

    // Создание Theme
    public ThemeResponse createTheme(ThemeRequest themeRequest) {
        Theme theme = mapToEntity(themeRequest);
        Theme savedTheme = themeRepository.save(theme);
        return toThemeResponse(savedTheme);
    }

    public ThemeResponse getThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found: " + id));
        return toThemeResponse(theme);
    }

    // Обновление Theme
    public ThemeResponse updateTheme(Long id, ThemeRequest themeRequest) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found: " + id));
        // Удаляем старый файл, если iconId меняется
        if (theme.getIcon() != null && !theme.getIcon().equals(themeRequest.getIconId())) {
            fileService.deleteFile(theme.getIcon().getId());
        }
        updateThemeFromRequest(theme, themeRequest);
        themeRepository.save(theme);
        return toThemeResponse(theme);
    }

    // Удаление Theme
    public void deleteTheme(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found theme with id " + id));
        // Удаляем связанный файл, если есть
        if (theme.getIcon() != null) {
            fileService.deleteFile(theme.getIcon().getId());
        }
        themeRepository.deleteById(id);
    }

    // Маппинг Page<Theme> в PageResponse<ThemeResponse>
    private PageResponse<ThemeResponse> mapToPageResponse(Page<Theme> themePage) {
        Page<ThemeResponse> responsePage = themePage.map(this::toThemeResponse);
        return new PageResponse<>(responsePage);
    }

    // Маппинг ThemeRequest в Theme
    private Theme mapToEntity(ThemeRequest themeRequest) {
        Theme theme = new Theme();
        theme.setName(themeRequest.getName());
        theme.setIsActive(themeRequest.getIsActive() != null ? themeRequest.getIsActive() : false);
        theme.setIsPopular(themeRequest.getIsPopular() != null ? themeRequest.getIsPopular() : false);
        theme.setCategory(categoryRepository.findById(themeRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + themeRequest.getCategoryId())));
        if (themeRequest.getIconId() != null) {
            theme.setIcon(iconRepository.findById(themeRequest.getIconId())
                    .orElseThrow(() -> new EntityNotFoundException("Icon not found: " + themeRequest.getIconId())));
        }
        return theme;
    }

    // Обновление Theme из ThemeRequest
    private void updateThemeFromRequest(Theme theme, ThemeRequest themeRequest) {
        theme.setName(themeRequest.getName());
        theme.setIsActive(themeRequest.getIsActive() != null ? themeRequest.getIsActive() : false);
        theme.setIsPopular(themeRequest.getIsPopular() != null ? themeRequest.getIsPopular() : false);
        theme.setCategory(categoryRepository.findById(themeRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + themeRequest.getCategoryId())));
        if (themeRequest.getIconId() != null) {
            theme.setIcon(iconRepository.findById(themeRequest.getIconId())
                    .orElseThrow(() -> new EntityNotFoundException("Icon not found: " + themeRequest.getIconId())));
        }
    }

    // Маппинг Theme в ThemeResponse
    private ThemeResponse toThemeResponse(Theme theme) {
        ThemeResponse response = new ThemeResponse();
        response.setId(theme.getId());
        response.setName(theme.getName());
        response.setIsActive(theme.getIsActive());
        response.setIsPopular(theme.getIsPopular());
        response.setCategoryId(theme.getCategory() != null ? theme.getCategory().getId() : null);
        response.setIconId(theme.getIcon() != null ? theme.getIcon().getId() : null);
        return response;
    }
}
