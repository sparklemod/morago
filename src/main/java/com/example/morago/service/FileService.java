package com.example.morago.service;

import com.example.morago.controller.dto.response.file.FileResponse;
import com.example.morago.model.entity.File;
import com.example.morago.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileResponse uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String uniqueFileName = UUID.randomUUID() + fileExtension;
        Path filePath = uploadPath.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), filePath);

        File fileEntity = new File();
        fileEntity.setOriginalTitle(originalFilename != null ? originalFilename : "unknown");
        fileEntity.setPath(filePath.toString());
        fileEntity.setType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");

        File savedFile = fileRepository.save(fileEntity);

        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(savedFile.getId());
        fileResponse.setOriginalTitle(savedFile.getOriginalTitle());
        fileResponse.setPath(savedFile.getPath());
        fileResponse.setType(savedFile.getType());
        return fileResponse;
    }

    public FileResponse getFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id " + id));
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(file.getId());
        fileResponse.setOriginalTitle(file.getOriginalTitle());
        fileResponse.setPath(file.getPath());
        fileResponse.setType(file.getType());
        return fileResponse;
    }
}
