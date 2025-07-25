package com.example.morago.config.storage;

import com.example.morago.config.storage.dto.AwsProps;
import com.example.morago.service.file.storage.FileStorage;
import com.example.morago.service.file.storage.LocalFileStorage;
import com.example.morago.service.file.storage.S3FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
@Profile("s3")
public class S3Config {
    @Bean
    @ConfigurationProperties(prefix = "file.aws")
    public AwsProps awsProps() {
        return new AwsProps();
    }

    @Bean
    public S3Client s3Client(AwsProps awsProps) {
        return S3Client.builder()
                .region(Region.of(awsProps.getS3().getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsProps.getS3().getAccessKey(), awsProps.getS3().getSecretKey())))
                .build();
    }
}
