package com.example.morago.repository;

import com.example.morago.model.entity.Notification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("""
            SELECT COUNT(c)
            FROM Notification c
            WHERE c.user.id = :userId
              AND c.isRead = :isRead
        """)
    Integer countNotifications(Long userId, boolean isRead);
    List<Notification> findAllByUserId(Long userId);
    Page<Notification> findAllByUserId(Long userId, Pageable pageable);
}
