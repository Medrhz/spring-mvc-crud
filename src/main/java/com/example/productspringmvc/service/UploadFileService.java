package com.example.productspringmvc.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class UploadFileService {
    private static final String UPLOAD_DIR = "public/";

    public String saveFile (MultipartFile file, String subPath) throws IOException {
        File uploadDir = null;
        String imagePath = null;

        if (file == null || file.isEmpty()) {  // ✅ Fix: Check null before calling isEmpty()
            return null;
        } else {
            if (subPath != null) {
                uploadDir = new File(UPLOAD_DIR + subPath + "/");
            } else {
                uploadDir = new File(UPLOAD_DIR);
            }
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // save file
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            imagePath = subPath == null ? fileName : subPath + "/" + fileName;
            File desitination = new File(UPLOAD_DIR + imagePath);
            Files.copy(file.getInputStream(), desitination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return imagePath;
        }

    }
}
