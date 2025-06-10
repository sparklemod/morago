package com.example.morago.repository;

import com.example.morago.model.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository
    extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

    @Query("SELECT o FROM UserProfile o WHERE LOWER(o.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(o.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<UserProfile> findByName(@Param("name") String name, Pageable pageable);
}

