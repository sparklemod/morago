package com.example.morago.controller.dto.requests.user;

import com.example.morago.controller.dto.requests.PageRequest;
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
public class UserGetRequest extends PageRequest {

    @Schema(description = "Имя пользователя для фильтрации", example = "Svetlana")
    private String firstName;

    @Schema(description = "Фамилия пользователя для фильтрации", example = "Popova")
    private String lastName;

    @Schema(description = "Телефон пользователя", example = "+70000000008")
    private String phone;

    @Schema(description = "Email пользователя", example = "sveta8@example.com")
    private String email;
}