package com.example.morago.controller;

import com.example.morago.model.entity.File;
import com.example.morago.model.enums.FileType;
import com.example.morago.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type")FileType type) {
        return fileService.uploadFile(file, type, null);
    }

    @GetMapping("/{id}")
    public String getFileUrl(@PathVariable Long id) {
        return fileService.getFileUrl(id);
    }
}
