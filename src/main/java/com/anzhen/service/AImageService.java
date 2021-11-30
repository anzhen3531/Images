package com.anzhen.service;

import com.anzhen.entity.AImage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 数据库图片存储表 服务类
 * </p>
 *
 * @author anzhen
 * @since 2021-11-13
 */
public interface AImageService extends IService<AImage> {

    /**
     * 上传图片
     *
     * @param multipartFile
     */
    void uploadFileAndDb(MultipartFile multipartFile);

    /**
     * 删除图片
     *
     * @param id
     */
    void delete(Long id);
}
