package com.example.morago.service;

import com.example.morago.exception.HandledException;
import com.example.morago.exception.NotFoundMessage;
import com.example.morago.model.entity.File;
import com.example.morago.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileRepository fileRepository;

    public File getFile(String url) {
        File image = null;
        if (url != null && !url.isEmpty()) {
            image = fileRepository.findByPath(url)
                .orElseThrow(() -> new HandledException(NotFoundMessage.IMAGE.format()));
        }

        return image;
    }
}
