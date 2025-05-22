package com.example.morago.controller.dto.requests;

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
public class TranslatorGetRequest {

    @Schema(description = "Имя переводчика для фильтрации", example = "Ivan")
    private String firstName;

    @Schema(description = "Фамилия переводчика для фильтрации", example = "Petrov")
    private String lastName;

    @Schema(description = "Телефон переводчика", example = "+70000000001")
    private String phone;

    @Schema(description = "Email переводчика", example = "ivan1@example.com")
    private String email;

    @Schema(description = "Онлайн статус", example = "true")
    private Boolean isOnline;

    @Schema(description = "Уровень владения корейским (от 1 до 5)", example = "3")
    private Integer levelOfKorean;

    @Schema(description = "Дата рождения (с) переводчика", example = "1990-01-01")
    private LocalDate dateOfBirthFrom;

    @Schema(description = "Дата рождения (до) переводчика", example = "2004-01-01")
    private LocalDate dateOfBirthTo;

    @Schema(description = "Номер страницы (с нуля)", example = "0", defaultValue = "0")
    private int page = 0;

    @Schema(description = "Размер страницы", example = "5", defaultValue = "5")
    private int size = 5;

    @Schema(description = "Поле для сортировки", example = "lastName", defaultValue = "id")
    private String sortBy = "id";

    @Schema(description = "Направление сортировки (asc / desc)", example = "asc", defaultValue = "asc")
    private String sortDirection = "asc";
}