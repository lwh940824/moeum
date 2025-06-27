package com.moeum.moeum.global.storage;

import com.moeum.moeum.type.FileType;

public record UploadFileDto(
        String originalFileName,
        String storedPath,
        FileType fileType
) {}
