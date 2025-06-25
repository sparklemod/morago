package com.example.morago.service.file.storage;

import com.example.morago.util.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    String saveFile(MultipartFile file, String key) throws FileUploadException;
    void deleteFile(String key) throws FileUploadException;
    String getFileUrl(String key);
}
