package com.anzhen.service.impl;

import com.anzhen.service.FileUploadService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;


@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {


    @Resource
    MinioClient minioClient;

    /**
     * 单张图片上传minio
     *
     * @param bucketName 同名称
     * @param file       文件
     */
    @SneakyThrows
    public void fileUpload(String bucketName, MultipartFile file) {
        // 构建文件上传对象
        PutObjectArgs objectArgs = PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType()).build();
        minioClient.putObject(objectArgs);
        log.info("图片上传成功");
    }

    @Override
    public void fileUpload(String bucketName, String filePath, InputStream inputStream) throws Exception {
        // 构建文件上传对象
        PutObjectArgs objectArgs = PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(filePath)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        minioClient.putObject(objectArgs);
        log.info("图片上传成功");
    }

    /**
     * 删除文件
     *
     * @param bucketName 桶名称
     * @param fileName   文件名称
     */
    @SneakyThrows
    public void delFile(String bucketName, String fileName) {
        RemoveObjectArgs removeObjectsArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();
        minioClient.removeObject(removeObjectsArgs);
    }


    /**
     * 获取minio文件的下载地址
     *
     * @param bucketName 桶名称
     * @param fileName   文件名
     */
    @SneakyThrows
    public String getFileUrl(String bucketName, String fileName) {
        GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .build();
        return minioClient.getPresignedObjectUrl(objectUrlArgs);
    }
}
