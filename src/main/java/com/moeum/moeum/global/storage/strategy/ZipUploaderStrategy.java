package com.moeum.moeum.global.storage.strategy;

import com.moeum.moeum.global.storage.dto.UploadFileDto;
import com.moeum.moeum.global.storage.service.StorageUploader;
import com.moeum.moeum.type.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ZipUploaderStrategy implements FileUploadStrategy {

    private final StorageUploader storageUploader;
    private final ZipExtractor zipExtractor;

    @Override
    public boolean supports(MultipartFile file) {
        return file.getOriginalFilename().toLowerCase().endsWith(".zip");
    }

    @Override
    public List<UploadFileDto> upload(MultipartFile zipFile, String path, FileType fileType) {
        List<MultipartFile> fileList = zipExtractor.unzip(zipFile);
        List<UploadFileDto> uploadFileDtoList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            uploadFileDtoList.add(storageUploader.upload(file, path, fileType));
        }
        return uploadFileDtoList;
    }
}
