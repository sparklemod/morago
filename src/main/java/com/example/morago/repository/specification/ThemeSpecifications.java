package com.example.morago.repository.specification;


import com.example.morago.model.entity.Theme;
import org.springframework.data.jpa.domain.Specification;

public class ThemeSpecifications {
    // Для админа
    public static Specification<Theme> adminFilter(String keyword, Boolean isActive, Long categoryId) {
        return Specification.where(nameContains(keyword))
                .and(isActive(isActive))
                .and(hasCategory(categoryId));
    }

    private static Specification<Theme> nameContains(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };
    }


    private static Specification<Theme> isActive(Boolean isActive) {
        return (root, query, builder) -> isActive == null ? builder.conjunction()
                : builder.equal(root.get("isActive"), isActive);
    }

    public static Specification<Theme> hasCategory(Long categoryId) {
        return (root, query, builder) -> categoryId == null ? builder.conjunction()
                : builder.equal(root.get("category").get("id"), categoryId);
    }
}
