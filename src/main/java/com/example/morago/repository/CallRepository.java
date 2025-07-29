package com.example.morago.repository;

import com.example.morago.model.entity.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    Page<Call> findByCallerId(Long userId, Pageable pageable);
    Page<Call> findByRecipientId(Long userId, Pageable pageable);

    @Query("""
            SELECT COUNT(c) > 0
            FROM Call c
            WHERE c.recipient.id = :translatorId
              AND c.caller.id = :callerId
              AND c.isEndCall = true
        """)
    boolean isFirstCall(@Param("translatorId") Long translatorId,
        @Param("callerId") Long callerId);

    @Query("""
            SELECT COUNT(c) > 0
            FROM Call c
            WHERE c.recipient.id = :translatorId
              AND c.isEndCall = false
        """)
    boolean hasActiveCall(@Param("translatorId") Long translatorId);

    @Query("""
                SELECT DISTINCT c.theme.id 
                FROM Call c 
                WHERE c.caller.id = :userId 
                   OR c.recipient.id = :userId 
                ORDER BY c.createdTime DESC
            """)
    List<Long> findTopThemeIdsByUserIdOrderByCallDateDesc(@Param("userId") Long userId, Pageable pageable);

}
