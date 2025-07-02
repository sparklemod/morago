package com.example.morago.model.dto.requests.translator;

import com.example.morago.model.dto.requests.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Фильтры и параметры пагинации для получения переводчиков")
public class TranslatorGetRequest extends PageRequest {

    @Schema(description = "Firstname | Lastname | phone | email", example = "sve")
    private String keyword;

    @Schema(description = "Translator's first name", example = "Ivan")
    private String firstName;

    @Schema(description = "Translator's last name", example = "Petrov")
    private String lastName;

    @Schema(description = "Translator's phone number", example = "+70000000001")
    private String phone;

    @Schema(description = "Translator's email address", example = "ivan1@example.com")
    private String email;

    @Schema(description = "Translator's online status", example = "false")
    private Boolean isOnline;

    @Schema(description = "Korean language proficiency level (from 1 to 5)", example = "4")
    private Integer levelOfKorean;

    @Schema(description = "Translator's date of birth (from)", example = "1985-05-12")
    private LocalDate dateOfBirthFrom;

    @Schema(description = "Translator's date of birth (to)", example = "2004-01-01")
    private LocalDate dateOfBirthTo;
}