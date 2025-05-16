package com.example.morago.repositories;

import com.example.morago.entity.impl.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByPath(String path);
    boolean existsByPath(String path);
}
