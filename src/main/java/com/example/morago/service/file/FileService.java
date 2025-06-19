package com.example.morago.service.file;

import com.example.morago.exception.FileUploadException;
import com.example.morago.model.entity.File;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.FileRepository;
import com.example.morago.service.file.storage.FileStorage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    @Qualifier("localFileStorage")
    private final FileStorage fileStorage;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public File uploadFile(MultipartFile file, FileType type) {
        validateFile(file, type);
        String key = generateKey(file.getOriginalFilename(), type);
        String path = fileStorage.saveFile(file, key);


        File fileEntity = new File();
        fileEntity.setOriginalTitle(file.getOriginalFilename());
        fileEntity.setPath(path);
        fileEntity.setSize(file.getSize());
        fileEntity.setType(file.getContentType());

        return fileRepository.save(fileEntity);
    }

    public File getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id " + id));
    }

    public Page<File> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

    public String getFileUrl(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id" + id));
        return fileStorage.getFileUrl(file.getPath());
    }

    public void deleteFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id " + id));
        fileStorage.deleteFile(file.getPath());
        fileRepository.delete(file);
    }

    // Валидация
    private void validateFile(MultipartFile file, FileType type) {
        if (file.isEmpty()) {
            throw new FileUploadException("File cannot be empty");
        }

        if (type == FileType.AVATAR) {
            if (file.getContentType().matches("image/jpeg|image/jpg|image/png")) {
                throw new FileUploadException("File type is image/jpeg or image/jpg");
            }
            if (file.getSize() > 10 * 1024 * 1024) { // 10 MB
                throw new FileUploadException("File size is too large, should be less than 10MB");
            }
        }
        // TODO: Добавить валидацию для DOCUMENT, CALL, ICON
    }

    private String generateKey(String originalFilename, FileType type) {
        String prefix = switch (type) {
            case AVATAR -> "avatars/";
            case DOCUMENT -> "documents/";
            case CALL -> "calls/";
            case ICON -> "icons/";
        };
        return prefix + UUID.randomUUID() + "-" + originalFilename;
    }
}
