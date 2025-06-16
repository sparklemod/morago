package com.example.morago.controller;

import com.example.morago.controller.dto.response.file.FileResponse;
import com.example.morago.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    @GetMapping("/id")
    public FileResponse getFileById(@RequestParam("id") long id) {
        return fileService.getFileById(id);
    }

}
