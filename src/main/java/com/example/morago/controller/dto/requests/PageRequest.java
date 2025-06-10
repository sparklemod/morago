package com.example.morago.controller.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

@Data
public class PageRequest {

    @Schema(description = "Номер страницы (с нуля)", example = "0")
    private int page = 0;

    @Schema(description = "Размер страницы", example = "5")
    private int size = 5;

    @Schema(description = "Поле для сортировки", example = "id")
    private String sortBy = "id";

    @Schema(description = "Направление сортировки ASC | DESC)", example = "ASC")
    @Enumerated(EnumType.STRING)
    private Direction sortDirection;
}
