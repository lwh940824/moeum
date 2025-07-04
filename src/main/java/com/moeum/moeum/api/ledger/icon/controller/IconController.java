package com.moeum.moeum.api.ledger.icon.controller;

import com.moeum.moeum.api.ledger.icon.dto.IconRequestDto;
import com.moeum.moeum.api.ledger.icon.dto.IconResponseDto;
import com.moeum.moeum.api.ledger.icon.service.IconService;
import com.moeum.moeum.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "아이콘")
@RestController
@RequestMapping("/api/icon")
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<IconResponseDto>> postIcon(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute IconRequestDto iconRequestDto) {
        List<IconResponseDto> uploadFileDtoList = iconService.uploadFile(userDetails.getId(), iconRequestDto.file());
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadFileDtoList);
    }
}
