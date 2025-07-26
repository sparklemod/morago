package com.example.morago.service.notification.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationDto {

    private String title;
    private String text;
    private LocalDateTime date;
    private Boolean isRead;

    public NotificationDto(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = LocalDateTime.now();
        this.isRead = false;
    }
}
