package com.anzhen.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 文件上传接口
     *
     * @param bucketName 同名称
     * @param file       文件
     */
    void fileUpload(String bucketName, MultipartFile file);

}
