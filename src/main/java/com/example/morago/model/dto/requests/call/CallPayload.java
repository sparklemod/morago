package com.example.morago.model.dto.requests.call;

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