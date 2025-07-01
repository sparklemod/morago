package com.example.morago.service;

import com.example.morago.controller.dto.requests.category.CategoryPageRequest;
import com.example.morago.controller.dto.requests.theme.ThemePageRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.repository.CategoryRepository;
import com.example.morago.repository.specification.CategorySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ThemeService themeService;

    // Публичный список категорий
    public Page<Category> getPublicCategories(CategoryPageRequest categoryPageRequest) {
        Specification<Category> spec = CategorySpecification.forAuthenticated();
        Pageable pageable = categoryPageRequest.toPageableWithoutSort();
        return categoryRepository.findAll(spec, pageable);
    }

    // Список категорий для Админа
    public Page<Category> getAdminCategories(CategoryPageRequest categoryPageRequest) {
        Specification<Category> spec = CategorySpecification.combineForAdmin(categoryPageRequest.getKeyword(),
                categoryPageRequest.getIsActive());
        Pageable pageable = categoryPageRequest.toPageableWithoutSort();
        return categoryRepository.findAll(spec, pageable);
    }

    // Получение категории по id
    public Category getCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    // Создание
    public Category createCategory(Category category) {
        validateCategory(category);
        category.setIsActive(category.getIsActive() != null ? category.getIsActive() : true);
        return categoryRepository.save(category);
    }

    // Редакрирование
    public Category updateCategory(Long id, Category category) {
        Category existing = getCategoryByIdOrThrow(id);
        validateCategory(category);
        existing.setName(category.getName());
        existing.setIsActive(category.getIsActive() != null ? category.getIsActive() : true);
        return categoryRepository.save(category);
    }

    // Мягкое удаление, для сохранения в истории и чтоб при случайном удалении не удалились темы
    public Category softDeleteCategoryById(Long id) {
        Category category = getCategoryByIdOrThrow(id);
        category.setIsActive(false);
        categoryRepository.save(category);
        return category;
    }

    // Получение тем по категории
    public PageResponse<ThemeResponse> getThemesByCategoryId(Long categoryId, ThemePageRequest themePageRequest, Long userId) {
        getCategoryByIdOrThrow(categoryId);
        themePageRequest.setCategoryId(categoryId);
        return themeService.getPublicThemes(themePageRequest, userId, categoryId);
    }

    // Валидация
    private void validateCategory(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (category.getName().length() > 100) {
            throw new IllegalArgumentException("Name must be less than 100 characters");
        }
    }

}
