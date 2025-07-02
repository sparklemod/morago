package com.example.morago.model.dto.requests.call;

import lombok.Data;

@Data
public class CallCreateRequest {
    private Long callerId;
    private Long recipientId;
    private Long themeId;
    private String channelName;
}
