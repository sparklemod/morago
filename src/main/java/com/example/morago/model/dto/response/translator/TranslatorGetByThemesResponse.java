package com.example.morago.model.dto.response.translator;

import com.example.morago.model.entity.Translator;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dto for getting translators filtered by theme")
public class TranslatorGetByThemesResponse {

    private Long id;
    private String nameWithInitials;
    private Integer levelOfKorean;
    private String imageUrl;
    private String theme;

    public static TranslatorGetByThemesResponse mapToDto(Translator t, String themeName) {
        return TranslatorGetByThemesResponse.builder()
            .id(t.getId())
            .nameWithInitials(t.getNameWithInitials())
            .levelOfKorean(t.getLevelOfKorean())
            .imageUrl(t.getImageFile() == null ? null : t.getImageFile().getPath())
            .theme(themeName)
            .build();
    }
}