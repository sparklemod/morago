package com.example.morago.config.storage.dto;

import lombok.Data;

@Data
public class AwsProps {
    private S3 s3;

    @Data
    public static class S3 {
        private String region;
        private String bucket;
        private String accessKey;
        private String secretKey;
    }
}
