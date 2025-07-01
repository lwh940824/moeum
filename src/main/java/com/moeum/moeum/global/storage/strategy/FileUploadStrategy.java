package com.moeum.moeum.global.storage.strategy;

import com.moeum.moeum.global.storage.dto.UploadFileDto;
import com.moeum.moeum.type.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadStrategy {
    boolean supports(MultipartFile file);
    List<UploadFileDto> upload(MultipartFile file, String path, FileType fileType);
}