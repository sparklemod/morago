package com.example.morago.service;

import com.example.morago.model.dto.requests.PageRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.requests.theme.ThemeRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.dto.response.theme.UserThemesResponse;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.ThemeRepository;
import com.example.morago.repository.UserProfileRepository;
import com.example.morago.repository.specification.ThemeSpecifications;
import com.example.morago.service.file.FileService;
import com.example.morago.util.exception.HandledException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserProfileRepository userProfileRepository;
    private final UserProfileService userProfileService;
    private CallService callService;

    @Autowired
    public void setCallService(@Lazy CallService callService) {
        this.callService = callService;
    }

    @Autowired
    public void setCategoryService(@Lazy CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Получение всех активных тем

    public PageResponse<ThemeResponse> getAllActiveThemes(PageRequest pageRequest) {
        Page<Theme> page = themeRepository.findAllByIsActiveTrue((Pageable) pageRequest);
        return new PageResponse<>(page.map(this::toThemeResponse));
    }

    // Получение тем по категории
    public PageResponse<ThemeResponse> getThemesByCategoryId(Long categoryId, PageRequest pageRequest) {
        Page<Theme> page = themeRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageRequest.toPageable());
        return new PageResponse<>(page.map(this::toThemeResponse));
    }

    // Получение популярных тем
    @Cacheable("popularThemes")
    public PageResponse<ThemeResponse> getPopularThemes(PageRequest pageRequest) {
        Page<Theme> page = themeRepository.findByIsPopularTrueAndIsActiveTrue(pageRequest.toPageable());
        return new PageResponse<>(page.map(this::toThemeResponse));
    }

    // Получение по последним звонкам
    public PageResponse<ThemeResponse> getLastCalledTheme(Long userId, PageRequest pageRequest) {
        List<Long> themesIds = callService.getLastCalledThemeIdsByUser(userId, pageRequest);
        if (themesIds.isEmpty()) {
            return new PageResponse<>(Page.empty());
        }
        Page<Theme> page = themeRepository.findByIdInAndIsActiveTrue(themesIds, pageRequest.toPageable());
        return new PageResponse<>(page.map(this::toThemeResponse));
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

    // Получение тем по переводчику
    public List<Theme> getByIds(Set<Long> ids) {
        return themeRepository.findAllByIdIn(new ArrayList<>(ids));
    }

    //Список тем для Админа
    public PageResponse<ThemeResponse> getAdminThemes(ThemePageRequest themePageRequest) {
        Specification<Theme> spec = ThemeSpecifications.adminFilter(
                themePageRequest.getKeyword(), themePageRequest.getIsActive(), themePageRequest.getCategoryId());
        Page<Theme> page = themeRepository.findAll(spec, themePageRequest.toPageable());
        return new PageResponse<>(page.map(this::toThemeResponse));
    }

    //Список любимых тем пользователя
    public UserThemesResponse getUserThemes(Long userId) {
        UserProfile user = userProfileService.findById(userId);

        List<Theme> favoriteThemes = user.getFavoriteThemes()
                .stream()
                .filter(Theme::getIsActive)
                .toList();

        List<Theme> defaultThemes;

        if (favoriteThemes.isEmpty()) {
            defaultThemes = themeRepository.findAllByIsActiveTrue();
        } else {
            List<Long> favoriteIds = favoriteThemes.stream()
                    .map(Theme::getId)
                    .toList();

            defaultThemes = themeRepository.findActiveThemesExcludingFavorites(favoriteIds);
        }

        return new UserThemesResponse(
                favoriteThemes.stream().map(this::toThemeResponse).toList(),
                defaultThemes.stream().map(this::toThemeResponse).toList()
        );
    }

    //Добавление темы в список любимых
    @Transactional
    public void addFavoriteTheme(Long userId, Long themeId) {
        UserProfile user = userProfileService.findById(userId);

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new HandledException("Theme not found: " + themeId));

        user.addFavoriteTheme(theme);
        userProfileRepository.save(user);
    }

    //Удаление темы из списка любимых
    @Transactional
    public void removeFavoriteTheme(Long userId, Long themeId) {
        UserProfile user = userProfileService.findById(userId);

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new HandledException("Theme not found: " + themeId));

        user.removeFavoriteTheme(theme);
        userProfileRepository.save(user);
    }

    // CRUD
    public ThemeResponse getThemeById(Long id) {
        Theme theme = getThemeOrThrow(id);
        return toThemeResponse(theme);
    }

    public ThemeResponse createTheme(ThemeRequest themeRequest) {
        Theme theme = new Theme();
        fillThemeFields(theme, themeRequest);
        return toThemeResponse(themeRepository.save(theme));
    }

    @CacheEvict(value = "popularThemes", allEntries = true)
    public ThemeResponse updateTheme(Long id, ThemeRequest themeRequest) {
        Theme theme = getThemeOrThrow(id);
        fillThemeFields(theme, themeRequest);
        Theme updatedTheme = themeRepository.save(theme);
        return toThemeResponse(updatedTheme);
    }

    public void deleteTheme(Long id) {
        Theme theme = getThemeOrThrow(id);
        // Удаляем связанный файл, если есть
        if (theme.getIcon() != null) {
            fileService.deleteFile(theme.getIcon().getId());
        }
        themeRepository.delete(theme);
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
        theme.setTitle(themeRequest.getTitle());
        theme.setDescription(themeRequest.getDescription());
        theme.setPrice(themeRequest.getPrice());
        theme.setNightPrice(themeRequest.getNightPrice());
        theme.setIsActive(themeRequest.getIsActive() != null ? themeRequest.getIsActive() : false);
        theme.setIsPopular(themeRequest.getIsPopular() != null ? themeRequest.getIsPopular() : false);
        theme.setIcon(themeRequest.getIconId() != null && themeRequest.getIconId() > 0 ? fileService.getFileById(themeRequest.getIconId()) : null);
        theme.setCategory(categoryService.getCategoryByIdOrThrow(themeRequest.getCategoryId()));
    }

    public Theme getThemeOrThrow(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found: " + id));
    }
}
