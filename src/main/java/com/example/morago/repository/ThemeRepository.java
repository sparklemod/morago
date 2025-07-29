package com.example.morago.repository;

import com.example.morago.model.entity.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>,
        JpaSpecificationExecutor<Theme> {

    Page<Theme> findByCategoryIdAndIsActiveTrue(Long categoryId, Pageable pageable);

    Page<Theme> findByIsPopularTrueAndIsActiveTrue(Pageable pageable);

    Page<Theme> findByIdInAndIsActiveTrue(List<Long> ids, Pageable pageable);

    Page<Theme> findAllByIsActiveTrue(Pageable pageable);


    List<Theme> findAllByIdIn(List<Long> ids);

    List<Theme> findAllByIsActiveTrue();

    @Query("SELECT t FROM Theme t WHERE t.isActive = true " +
            "AND (:favoriteIds IS NULL OR t.id NOT IN :favoriteIds) " +
            "ORDER BY t.name ASC")
    List<Theme> findActiveThemesExcludingFavorites(@Param("favoriteIds") List<Long> favoriteIds);
}
