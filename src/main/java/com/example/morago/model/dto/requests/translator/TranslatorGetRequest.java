package com.example.morago.model.dto.requests.translator;

import com.example.morago.model.dto.requests.PageRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Parameter(description = "Firstname | Lastname | phone | email", example = "sve")
    private String keyword;

    @Parameter(description = "Is Translator active", example = "true")
    private Boolean isActive;

    @Parameter(description = "Has translator withdrawal", example = "true")
    private Boolean hasWithdrawal;
}