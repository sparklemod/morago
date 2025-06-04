package com.example.morago.controller.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallPayload {
    private String from;
    private String to;
    private String roomId;
}