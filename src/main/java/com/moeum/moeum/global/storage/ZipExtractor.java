package com.moeum.moeum.global.storage;

import com.moeum.moeum.global.exception.CustomException;
import com.moeum.moeum.global.exception.ErrorCode;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

                    files.add(new MockMultipartFile(filename, zipInputStream));
                }
            }

            return files;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNZIP_ERROR);
        }
    }
}