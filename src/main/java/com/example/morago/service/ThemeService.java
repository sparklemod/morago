package com.example.morago.service;

import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.requests.theme.ThemeRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.specification.ThemeSpecifications;
import com.example.morago.service.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final FileService fileService;
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(@Lazy CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    // Создание Theme
    @PreAuthorize("hasRole('ADMIN')")
    public ThemeResponse createTheme(ThemeRequest themeRequest) {
        Theme theme = new Theme();
        fillThemeFields(theme, themeRequest);
        return toThemeResponse(themeRepository.save(theme));
    }

    // Обновление Theme
    @PreAuthorize("hasRole('ADMIN')")
    public ThemeResponse updateTheme(Long id, ThemeRequest themeRequest) {
        Theme theme = getThemeOrThrow(id);
        fillThemeFields(theme, themeRequest);
        Theme updatedTheme = themeRepository.save(theme);
        return toThemeResponse(updatedTheme);
    }

    // Обновление иконки Theme
    public ThemeResponse updateThemeIcon(Long id, MultipartFile iconFile) {
        Theme theme = getThemeOrThrow(id);
        if (iconFile != null) {
            Long existingFileId = theme.getIcon() != null ? theme.getIcon().getId() : null;
            File icon = fileService.uploadFile(iconFile, FileType.ICON, existingFileId);
            theme.setIcon(icon);
            themeRepository.save(theme);
        }
        return toThemeResponse(theme);
    }

    // Удаление Theme
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTheme(Long id) {
        Theme theme = getThemeOrThrow(id);
        // Удаляем связанный файл, если есть
        if (theme.getIcon() != null) {
            fileService.deleteFile(theme.getIcon().getId());
        }
        themeRepository.delete(theme);
    }

    // Получение тем по переводчику
    public List<Theme> getByIds(Set<Long> ids) {
        return themeRepository.findAllByIdIn(new ArrayList<>(ids));
    }

    // Публичный список тем
    @PreAuthorize("isAuthenticated() or #userId == null")
    public PageResponse<ThemeResponse> getPublicThemes(
            ThemePageRequest themePageRequest,
            Long userId,
            Long categoryId) {
        Specification<Theme> spec = userId != null // Сортировка в Specification
                ? ThemeSpecifications.forAuthenticated(themePageRequest.getKeyword(), userId)
                .and(ThemeSpecifications.hasCategory(categoryId))
                : ThemeSpecifications.forAnonymous().and(ThemeSpecifications.hasCategory(categoryId));
        Pageable pageable = themePageRequest.toPageableWithoutSort();
        Page<Theme> page = themeRepository.findAll(spec, pageable);
        return mapToPageResponse(page);
    }

    //Список тем для Админа
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<ThemeResponse> getAdminThemes(ThemePageRequest themePageRequest) {
        Specification<Theme> spec = ThemeSpecifications.combineForAdmin(
                themePageRequest.getKeyword(), themePageRequest.getIsActive(), themePageRequest.getCategoryId());
        Pageable pageable = themePageRequest.toPageable();

        Page<Theme> themePage = themeRepository.findAll(spec, pageable);
        return mapToPageResponse(themePage);
    }

    public ThemeResponse getThemeById(Long id) {
        Theme theme = getThemeOrThrow(id);
        return toThemeResponse(theme);
    }

    // Маппинг Page<Theme> в PageResponse<ThemeResponse>
    private PageResponse<ThemeResponse> mapToPageResponse(Page<Theme> themePage) {
        Page<ThemeResponse> responsePage = themePage.map(this::toThemeResponse);
        return new PageResponse<>(responsePage);
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

    // Обновление полей
    private void fillThemeFields(Theme theme, ThemeRequest themeRequest) {
        theme.setName(themeRequest.getName());
        theme.setIsActive(themeRequest.getIsActive() != null ? themeRequest.getIsActive() : false);
        theme.setIsPopular(themeRequest.getIsPopular() != null ? themeRequest.getIsPopular() : false);
        theme.setCategory(categoryService.getCategoryByIdOrThrow(themeRequest.getCategoryId()));
    }

    public Theme getThemeOrThrow(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found: " + id));
    }
}
