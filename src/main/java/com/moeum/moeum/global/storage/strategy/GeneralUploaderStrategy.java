package com.moeum.moeum.global.storage.strategy;

import com.moeum.moeum.global.storage.UploadFileDto;
import com.moeum.moeum.global.storage.service.StorageUploader;
import com.moeum.moeum.type.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeneralUploaderStrategy implements FileUploadStrategy {

    private final StorageUploader storageUploader;

    @Override
    public boolean supports(MultipartFile file) {
        return !file.getOriginalFilename().toLowerCase().endsWith(".zip");
    }

    @Override
    public List<UploadFileDto> upload(MultipartFile file, String path, FileType fileType) {
        return storageUploader.upload(file, path, fileType);
    }
}
