package com.example.morago.model.dto.response.translator;

import com.example.morago.model.entity.Translator;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
    @Schema(description = "Dto for getting translators")
public class TranslatorGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Boolean isOnline;
    private Integer levelOfKorean;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate dateOfBirth;

    private Boolean hasWithdrawalRequest;

    public static TranslatorGetResponse mapToDto(Translator t) {
        return TranslatorGetResponse.builder()
            .id(t.getId())
            .firstName(t.getFirstName())
            .lastName(t.getLastName())
            .phone(t.getPhone())
            .email(t.getEmail())
            .isOnline(t.getIsOnline())
            .levelOfKorean(t.getLevelOfKorean())
            .dateOfBirth(t.getDateOfBirth())
            .hasWithdrawalRequest(t.hasActiveWithdrawal())
            .build();
    }
}