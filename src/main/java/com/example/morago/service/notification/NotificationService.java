package com.example.morago.service.notification;

import com.example.morago.model.dto.requests.notification.NotificationGetCountRequest;
import com.example.morago.model.entity.Admin;
import com.example.morago.model.entity.Notification;
import com.example.morago.model.entity.base.User;
import com.example.morago.repository.AdminRepository;
import com.example.morago.repository.NotificationRepository;
import com.example.morago.service.notification.dto.NotificationDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final AdminRepository adminRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void createAndSendNotificationToAdmins(NotificationDto dto) {
        List<Admin> admins = adminRepository.findAll();

        List<Notification> savedNotifications = new ArrayList<>();
        for (Admin admin : admins) {
            Notification notification = createNotification(dto);
            notification.setUser(admin);
            savedNotifications.add(notification);

            sendNotification(notification);
        }

        repository.saveAll(savedNotifications);
    }

    public void createAndSendNotificationToUser(NotificationDto dto, User user) {
        Notification notification = createNotification(dto);
        notification.setUser(user);
        sendNotification(notification);

        repository.save(notification);
    }

    public Page<Notification> getAllUserNotifications(Long userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    public Integer getCount(Long userId, NotificationGetCountRequest req) {
        return repository.countNotifications(userId, !req.getIsUnread());
    }

    public void clearAllUserNotifications(Long userId) {
        List<Notification> notifications = repository.findAllByUserId(userId);
        repository.deleteAll(notifications);
    }

    private Notification createNotification(NotificationDto dto) {
        return Notification.builder()
            .title(dto.getTitle())
            .text(dto.getText())
            .date(dto.getDate())
            .isRead(dto.getIsRead())
            .build();
    }

    private void sendNotification(Notification notification) {
        messagingTemplate.convertAndSendToUser(
            String.valueOf(notification.getUser().getId()),
            "/topic/notification",
            notification
        );
    }
}