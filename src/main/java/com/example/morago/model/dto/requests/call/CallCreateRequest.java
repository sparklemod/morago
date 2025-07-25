package com.example.morago.model.dto.requests.call;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CallCreateRequest {
    @NotNull
    private Long recipientId;

    @NotNull
    private Long themeId;

    @NotNull
    private LocalDateTime startTime;
}
