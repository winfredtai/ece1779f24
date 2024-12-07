package com.team9.ece1779f24.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalName.substring(originalName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;
        File folder = new File(path);
        // check if {path} is nested directory
        boolean isNestedDir = path.contains("/") || path.contains("\\");
        if (!folder.exists()) {
            boolean created;
            if (isNestedDir) {
                created = folder.mkdirs();
            } else {
                created = folder.mkdir();
            }
            if (!created) {
                throw new IOException("Failed to create directory at path: " + path);
            }
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
