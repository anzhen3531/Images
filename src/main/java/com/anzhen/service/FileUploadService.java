package com.anzhen.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件上传服务
 *
 * @author AnZhen
 */
public interface FileUploadService {

    /**
     * 文件上传接口  minio
     *
     * @param bucketName 同名称
     * @param file       文件
     */
    void fileUpload(String bucketName, MultipartFile file);

    /**
     * 文件上传接口
     *
     * @param bucketName
     * @param filePath
     * @param inputStream
     */
    void fileUpload(String bucketName, String filePath, InputStream inputStream, Integer objectSize) throws Exception;

    /**
     * minio 进行图片存储
     *
     * @return
     */
    void delFile(String bucketName, String fileName);

    /**
     * 获取图片路径
     *
     * @param bucketName 桶名称
     * @param fileName   文件名称
     * @return
     */
    String getFileUrl(String bucketName, String fileName);
}
