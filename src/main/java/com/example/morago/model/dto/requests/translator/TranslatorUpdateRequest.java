package com.example.morago.model.dto.requests.translator;

import com.example.morago.model.dto.requests.UserUpdateRequest;
import com.example.morago.model.entity.Language;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.Translator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Translator create request")
public class TranslatorUpdateRequest extends UserUpdateRequest {

    @Schema(description = "Level of Korean", example = "3")
    @Min(value = 1, message = "Level of Korean must be at least 1")
    @Max(value = 5, message = "Level of Korean cannot exceed 5")
    private Integer levelOfKorean;

    @Schema(description = "Date of birth", example = "1993.03.06")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    @Schema(description = "ThemeIds", example = "1,2")
    private Set<Long> themeIds = new HashSet<>();

    @Schema(description = "LanguageIds", example = "1,2")
    private Set<Long> languageIds = new HashSet<>();

    public Translator build(Translator translator, Set<Theme> themes, Set<Language> languages) {
        return translator
            .toBuilder()
            .firstName(firstName)
            .lastName(lastName)
            .levelOfKorean(levelOfKorean)
            .themes(themes)
            .dateOfBirth(dateOfBirth)
            .languages(languages)
            .build();
    }
}