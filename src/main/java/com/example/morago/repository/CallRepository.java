package com.example.morago.repository;

import com.example.morago.model.entity.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
