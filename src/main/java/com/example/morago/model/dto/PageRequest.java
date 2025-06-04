package com.example.morago.model.dto;

import lombok.Data;

@Data
public class PageRequest {
    private int page = 0;
    private int pageSize = 10;
    private String sort = "id,asc";
}
