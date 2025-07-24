package com.example.morago.model.dto.response.calls;

import com.example.morago.model.entity.Call;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.entity.base.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record CallsGetHistoryResponse(
    @JsonFormat(pattern = "yyyy.MM.dd")
    String date,
    String phone,
    Integer duration,
    BigDecimal coins,
    String theme,
    Byte rating,
    Boolean hasRequest
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    public static CallsGetHistoryResponse mapToDto(Call call, User user) {
        CallsGetHistoryResponseBuilder builder = CallsGetHistoryResponse.builder()
            .phone(user.getPhone())
            .date(call.getCreatedTime().format(FORMATTER))
            .duration(call.getDuration())
            .coins(call.getSum())
            .theme(call.getTheme().getName());

        if (user instanceof UserProfile up) {
            builder
                .rating(call.getUserRating())
                .hasRequest(up.hasActiveDeposit());
        } else if (user instanceof Translator t) {
            builder
                .rating(call.getTranslatorRating())
                .hasRequest(t.hasActiveWithdrawal());
        }

        return builder.build();
    }
}
