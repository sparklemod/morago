package com.example.morago.model.dto.response.translator;

import com.fasterxml.jackson.annotation.JsonFormat;
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
}