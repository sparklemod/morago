package com.example.morago.model.dto.requests.notification;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationGetCountRequest {
    @Parameter(required = true, description = "Is unread", example = "true")
    private Boolean isUnread;
}