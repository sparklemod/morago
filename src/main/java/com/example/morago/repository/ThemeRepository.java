package com.example.morago.repository;

import com.example.morago.model.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>,
        JpaSpecificationExecutor<Theme> {
    List<Theme> findAllByIdIn(Set<Long> ids);
}
