package com.anzhen.service.impl;

import com.anzhen.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    MinioClient minioClient;

    public void fileUpload(String bucketName, MultipartFile file) {
        try {
            // 构建文件上传对象
            PutObjectArgs objectArgs = PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType()).build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
