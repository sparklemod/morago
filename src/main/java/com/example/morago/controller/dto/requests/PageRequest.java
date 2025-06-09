package com.example.morago.controller.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageRequest {

    @Schema(description = "Номер страницы (с нуля)", example = "0", defaultValue = "0")
    private int page = 0;

    @Schema(description = "Размер страницы", example = "5", defaultValue = "5")
    private int size = 5;

    @Schema(description = "Поле для сортировки", example = "lastName", defaultValue = "id,asc")
    private String sortBy = "id";

    @Schema(description = "Направление сортировки (asc / desc)", example = "asc", defaultValue = "asc")
    private String sortDirection = "asc";

    public Pageable toPageableWithoutSort() {
        return org.springframework.data.domain.PageRequest.of(page, size);
    }
}
