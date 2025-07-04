package com.moeum.moeum.global.storage.service;

import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.global.storage.dto.UploadFileDto;
import com.moeum.moeum.global.storage.minio.MinioProperties;
import com.moeum.moeum.type.FileType;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioUploaderService implements StorageUploader {

    private final MinioClient minioClient;
    private final MinioProperties properties;

    @Override
    public UploadFileDto upload(MultipartFile file, String path, FileType fileType) {
        String bucket = properties.getBucket();
        String uploadPath = path + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                throw new CustomException(ErrorCode.NOT_FOUND_BUCKET);
            }

            if(FileType.ICON == fileType && ImageIO.read(file.getInputStream()) == null) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_TYPE_ERROR);
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(uploadPath)
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );

            return new UploadFileDto(
                    file.getOriginalFilename(),
                    uploadPath,
                    fileType
            );
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }
}
