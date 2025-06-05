package com.example.morago.controller.dto.requests.call;

import lombok.Data;

@Data
public class CallCreateRequest {
    private Long callerId;
    private Long recipientId;
    private Long themeId;
    private String channelName;
}
