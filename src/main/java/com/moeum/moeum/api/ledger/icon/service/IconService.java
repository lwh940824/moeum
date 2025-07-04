package com.moeum.moeum.api.ledger.icon.service;

import com.moeum.moeum.api.ledger.icon.dto.IconResponseDto;
import com.moeum.moeum.global.storage.strategy.FileUploadDispatcher;
import com.moeum.moeum.type.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IconService {

    private final FileUploadDispatcher fileUploadDispatcher;

    public List<IconResponseDto> uploadFile(Long userId, MultipartFile file) {
        FileType iconType = FileType.ICON;
        String path = userId + "/" + iconType;
        return fileUploadDispatcher.upload(file, path, iconType).stream()
                .map(uploadFileDto -> new IconResponseDto(uploadFileDto.storedPath()))
                .toList();
    }
}