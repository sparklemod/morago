package com.example.morago.repository;

import com.example.morago.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository
    extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
}

