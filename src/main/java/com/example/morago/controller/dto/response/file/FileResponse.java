package com.example.morago.controller.dto.response.file;

import lombok.Data;

@Data
public class FileResponse {
    private Long id;
    private String originalTitle;
    private String path;
    private String type;
}
