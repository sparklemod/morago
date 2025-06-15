package com.example.morago.controller.dto.requests.user;

import com.example.morago.controller.dto.requests.PageRequest;
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
@Schema(description = "Фильтры и параметры пагинации для получения пользователей")
public class UserGetRequest extends PageRequest {

    @Parameter(description = "Firstname | Lastname | phone | email", example = "sve")
    private String keyword;
}