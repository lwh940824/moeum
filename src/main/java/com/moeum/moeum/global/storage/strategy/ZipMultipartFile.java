package com.moeum.moeum.global.storage.strategy;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Builder
public class ZipMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String originalFilename;
    private final String contentType;

    @NotNull @Override public String getName() {return originalFilename;}
    @NotNull @Override public String getOriginalFilename() { return originalFilename; }
    @NotNull @Override public String getContentType() { return contentType; }
    @Override public boolean isEmpty() { return content.length == 0; }
    @Override public long getSize() { return content.length; }
    @NotNull @Override public byte[] getBytes() { return content; }
    @NotNull @Override public InputStream getInputStream() { return new ByteArrayInputStream(content); }
    @Override public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.write(dest.toPath(), content);
    }
}
