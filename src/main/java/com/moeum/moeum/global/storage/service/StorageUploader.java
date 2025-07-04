package com.moeum.moeum.global.storage.service;

import com.moeum.moeum.global.storage.dto.UploadFileDto;
import com.moeum.moeum.type.FileType;
import org.springframework.web.multipart.MultipartFile;

public interface StorageUploader {

    UploadFileDto upload(MultipartFile file, String path, FileType fileType);
}