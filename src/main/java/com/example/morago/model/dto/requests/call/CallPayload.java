package com.example.morago.model.dto.requests.call;

import com.example.morago.model.entity.Call;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CallPayload {
    private Long callerId;
    private Long translatorId;
    private String theme;
    private String callerName;
    private BigDecimal costPerMinute;
    private Boolean isFirst;

    public static CallPayload build(Call call)
    {
        return CallPayload.builder()
            .callerId(call.getCaller().getId())
            .translatorId(call.getRecipient().getId())
            .theme(call.getTheme().getName())
            .callerName(call.getCaller().getNameWithSurname())
            .costPerMinute(call.getTheme().getPrice())
            .isFirst(call.getIsFirst())
            .build();
    }
}