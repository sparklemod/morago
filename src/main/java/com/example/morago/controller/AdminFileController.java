package com.example.morago.controller;

import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.model.entity.File;
import com.example.morago.service.file.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/files")
@RequiredArgsConstructor
public class AdminFileController {
    private final FileService fileService;

    @GetMapping
    public PageResponse<File> getAllFiles(@Valid @ModelAttribute PageRequest pageRequest) {
        Page<File> filePage = fileService.getAllFiles(pageRequest);
        return new PageResponse<>(filePage);
    }

    @GetMapping("/{id}")
    public File getFile(@PathVariable Long id) {
        return fileService.getFile(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }


}
