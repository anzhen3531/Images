package com.anzhen.service.impl;

import com.anzhen.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


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
            log.info("图片上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // todo 编写文件下载

    public void fileDownLoad() {

    }


    // todo 编写文件查看
    public String getPath() {
        return null;
    }


}
