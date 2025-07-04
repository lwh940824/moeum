package com.moeum.moeum.api.ledger.icon.dto;

import org.springframework.web.multipart.MultipartFile;

public record IconRequestDto(
        MultipartFile file
) {}
