package com.example.morago.controller.dto.requests.translator;

import com.example.morago.controller.dto.requests.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Фильтры и параметры пагинации для получения переводчиков")
public class TranslatorGetRequest extends PageRequest {

    @Schema(description = "Имя переводчика для фильтрации", example = "Ivan")
    private String firstName;

    @Schema(description = "Фамилия переводчика для фильтрации", example = "Petrov")
    private String lastName;

    @Schema(description = "Телефон переводчика", example = "+70000000001")
    private String phone;

    @Schema(description = "Email переводчика", example = "ivan1@example.com")
    private String email;

    @Schema(description = "Онлайн статус", example = "false")
    private Boolean isOnline;

    @Schema(description = "Уровень владения корейским (от 1 до 5)", example = "4")
    private Integer levelOfKorean;

    @Schema(description = "Дата рождения (с) переводчика", example = "1985-05-12")
    private LocalDate dateOfBirthFrom;

    @Schema(description = "Дата рождения (до) переводчика", example = "2004-01-01")
    private LocalDate dateOfBirthTo;
}