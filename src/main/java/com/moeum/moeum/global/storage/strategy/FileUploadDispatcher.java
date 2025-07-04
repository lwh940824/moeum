package com.moeum.moeum.global.storage.strategy;

import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import com.moeum.moeum.global.storage.dto.UploadFileDto;
import com.moeum.moeum.type.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadDispatcher {

    private final List<FileUploadStrategy> strategyList;

    public List<UploadFileDto> upload (MultipartFile file, String path, FileType fileType) {
        return strategyList.stream()
                .filter(strategy -> strategy.supports(file))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_UPLOAD_ERROR))
                .upload(file, path, fileType);
    }
}
