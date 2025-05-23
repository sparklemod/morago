package com.example.morago.controller.dto.requests.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Фильтры и параметры пагинации для получения пользователей")
public class UserGetRequest {

    @Schema(description = "Имя пользователя для фильтрации", example = "Svetlana")
    private String firstName;

    @Schema(description = "Фамилия пользователя для фильтрации", example = "Popova")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+70000000008")
    private String phone;

    @Schema(description = "Email пользователя", example = "sveta8@example.com")
    private String email;

    @Schema(description = "Номер страницы (с нуля)", example = "0", defaultValue = "0")
    private int page = 0;

    @Schema(description = "Размер страницы", example = "5", defaultValue = "5")
    private int size = 5;

    @Schema(description = "Поле для сортировки", example = "lastName", defaultValue = "id")
    private String sortBy = "id";

    @Schema(description = "Направление сортировки (asc / desc)", example = "asc", defaultValue = "asc")
    private String sortDirection = "asc";
}