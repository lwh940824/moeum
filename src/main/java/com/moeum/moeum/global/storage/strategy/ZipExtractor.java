package com.moeum.moeum.global.storage.strategy;

import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ZipExtractor {

    public List<MultipartFile> unzip(MultipartFile file) {
        try {
            List<MultipartFile> files = new ArrayList<>();
            ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    String filename = zipEntry.getName();

                    files.add(ZipMultipartFile.builder()
                                    .content(zipInputStream.readAllBytes())
                                    .originalFilename(filename)
                                    .contentType(detectContentType(filename))
                            .build());
                }
            }

            return files;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNZIP_ERROR);
        }
    }

    private String detectContentType(String filename) {
        String ext = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1).toLowerCase())
                .orElse("");

        return switch (ext) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            case "svg" -> "image/svg+xml";
            case "txt" -> "text/plain";
            case "pdf" -> "application/pdf";
            case "zip" -> "application/zip";
            default -> "application/octet-stream"; // fallback
        };
    }
}