package com.moeum.moeum.global.storage.service;

import com.moeum.moeum.global.storage.UploadFileDto;
import com.moeum.moeum.type.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageUploader {

    List<UploadFileDto> upload(MultipartFile file, String path, FileType fileType);
}
