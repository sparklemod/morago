package com.example.morago.service.file;

import com.example.morago.util.exception.FileUploadException;
import com.example.morago.util.exception.HandledException;
import com.example.morago.model.entity.File;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.FileRepository;
import com.example.morago.service.file.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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

    // Загрузка нового файла или обновление предущего
    public File uploadFile(MultipartFile uploadedFile, FileType type, Long existingFileId) {
        validateFile(uploadedFile);
        String key = generateKey(uploadedFile.getOriginalFilename(), type);

        File fileToSave = new File();

        if (existingFileId != null) {
            fileToSave = getFileById(existingFileId);
        }

        return saveFile(uploadedFile, key, fileToSave);
    }

    public File getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new HandledException("File not found with id " + id));
    }

    public Page<File> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

    public void deleteFile(Long id) {
        File file = getFileById(id);
        fileStorage.deleteFile(file.getPath());
        fileRepository.delete(file);
    }

    // Валидация
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("File cannot be empty");
        }
    }

    private String generateKey(String originalFilename, FileType type) {
        String prefix = switch (type) {
            case AVATAR -> "avatars/";
            case ICON -> "icons/";
        };
        return prefix + UUID.randomUUID() + "-" + originalFilename;
    }

    // Сохранение файла
    private File saveFile(MultipartFile file, String key, File fileEntity) {
        if (fileEntity.getPath() != null) {
            fileStorage.deleteFile(fileEntity.getPath());
        }
        String path = fileStorage.saveFile(file, key);
        fileEntity.setOriginalTitle(file.getOriginalFilename());
        fileEntity.setPath(path);
        fileEntity.setSize(file.getSize());
        fileEntity.setType(file.getContentType());
        return fileRepository.save(fileEntity);
    }
}
