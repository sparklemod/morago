package com.example.morago.service.file.storage;

import com.example.morago.util.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@Primary
@Profile("local")
public class LocalFileStorage implements FileStorage {
    private final Path uploadDir;

    public LocalFileStorage(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir);
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new FileUploadException("Creation uploads directory failed" + e.getMessage());
        }
    }

    @Override
    public String saveFile(MultipartFile file, String key) throws FileUploadException {
        try {
            Path path = uploadDir.resolve(key);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e) {
            throw new FileUploadException("Saving file failed" + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String key) throws FileUploadException {
        try {
            Files.deleteIfExists(uploadDir.resolve(key));
        } catch (IOException e) {
            throw new FileUploadException("Deleting file failed" + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String key) throws FileUploadException {
        return "/uploads/" + Paths.get(key).getFileName().toString();
    }
}




