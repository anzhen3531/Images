package com.anzhen.service;

import com.anzhen.config.MinioProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RestController
class FileUploadServiceTest {

    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    MinioProperties minioProperties;

    @Test
    @PostMapping
    void fileUpload(MultipartFile multipartFile) throws Exception {

    }
}