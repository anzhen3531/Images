package com.anzhen.service;

import com.anzhen.entity.AImage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据库图片存储表 服务类
 * </p>
 *
 * @author anzhen
 * @since 2021-11-13
 */
public interface AImageService extends IService<AImage> {


    Page<AImage> mainView(Integer currentPage, Integer size);

    /**
     * 查询全部的图片
     */
    List<AImage> findMainView();

    /**
     * 上传图片
     *
     * @param multipartFile
     */
    void uploadFileAndDb(MultipartFile multipartFile);

    /**
     * 通过流上传文件
     *
     * @param inputStream
     * @param filePath
     * @throws Exception
     */
    void uploadFileAndDb(InputStream inputStream, String filePath, Integer objectSize) throws Exception;

    /**
     * 删除图片
     *
     * @param id
     */
    void delete(String id);

}
