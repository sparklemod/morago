package com.example.morago.service.file.storage;

import com.example.morago.config.storage.dto.AwsProps;
import com.example.morago.util.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
@Profile("s3")
@RequiredArgsConstructor
@Slf4j
public class S3FileStorage implements FileStorage {

    private final S3Client s3Client;
    private final AwsProps awsProps;

    @Override
    public String saveFile(MultipartFile file, String key) throws FileUploadException {
        try {
            PutObjectRequest put = PutObjectRequest.builder()
                    .bucket(awsProps.getS3().getBucket())
                    .key(key)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();
            s3Client.putObject(put, RequestBody.fromBytes(file.getBytes()));
            log.debug("Uploaded {} to bucket {}", key, awsProps.getS3().getBucket());
            return key;
        } catch (IOException | S3Exception e) {
            log.error("File upload failed: {}", e.getMessage());
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String key) throws FileUploadException {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(awsProps.getS3().getBucket())
                    .key(key)
                    .build());
            log.debug("Deleted {} from bucket {}", key, awsProps.getS3().getBucket());
        } catch (S3Exception e) {
            log.error("File delete failed: {}", e.getMessage());
            throw new FileUploadException("File delete failed: " + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String key) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(awsProps.getS3().getBucket())
                .key(key)
                .build()).toExternalForm();
    }
}
