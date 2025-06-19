package com.example.morago.service.file.storage;

import com.example.morago.exception.FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3FileStorage implements FileStorage {

    @Override
    public String saveFile(MultipartFile file, String key) throws FileUploadException {
        throw new UnsupportedOperationException("S3 не настроен");
    }

    @Override
    public void deleteFile(String key) throws FileUploadException {
        throw new UnsupportedOperationException("S3 не настроен");
    }

    @Override
    public String getFileUrl(String key) {
        throw new UnsupportedOperationException("S3 не настроен");
    }
}
