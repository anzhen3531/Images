package com.anzhen.service.impl;

import com.anzhen.config.minio.MinioProperties;
import com.anzhen.entity.AImage;
import com.anzhen.mapper.AImageMapper;
import com.anzhen.service.FileUploadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据库图片存储表 服务实现类
 * </p>
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Service
public class AImageServiceImpl
        extends ServiceImpl<AImageMapper, AImage>
        implements com.anzhen.service.AImageService {

    @Resource
    FileUploadService fileUploadService;
    @Resource
    MinioProperties properties;
    @Resource
    AImageMapper aImageMapper;

    @Override
    public Page<AImage> mainView(Integer currentPage, Integer size) {
        Page<AImage> page = new Page<>(currentPage, size);
        QueryWrapper<AImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        return aImageMapper.selectPage(page, queryWrapper);

    }

    @Override
    public List<AImage> findMainView() {
        return aImageMapper.selectList(null);
    }

    @Override
    public void uploadFileAndDb(MultipartFile multipartFile) {
        fileUploadService.fileUpload(properties.getBucket(), multipartFile);
        String fileUrl = multipartFile.getOriginalFilename();
        // 将文件设置进去数据库
        AImage aImage = new AImage();
        aImage.setImagePath(fileUrl);
        aImageMapper.insert(aImage);
    }

    @Override
    public void uploadFileAndDb(InputStream inputStream, String filePath, Integer objectSize) throws Exception {
        fileUploadService.fileUpload(properties.getBucket(), filePath, inputStream, objectSize);
        // 将文件设置进去数据库
        AImage aImage = new AImage();
        // 设置默认属性
        aImage.setImageId("pc" + System.currentTimeMillis());
        aImage.setState(1);
        aImage.setImagePath(filePath);
        aImageMapper.insert(aImage);
    }

    @Override
    public void delete(Long id) {
        QueryWrapper<AImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        AImage aImage = aImageMapper.selectOne(queryWrapper);
        fileUploadService.delFile(properties.getBucket(), aImage.getImagePath());
        aImageMapper.deleteById(id);
    }
}
