package com.anzhen.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.anzhen.common.context.AUserContext;
import com.anzhen.common.context.AUserContextHolder;
import com.anzhen.common.result.CommonState;
import com.anzhen.common.utils.InputStreamCacher;
import com.anzhen.config.minio.MinioProperties;
import com.anzhen.entity.AImage;
import com.anzhen.entity.AUser;
import com.anzhen.entity.AUserBackList;
import com.anzhen.mapper.AImageMapper;
import com.anzhen.service.AUserBackListService;
import com.anzhen.service.FileUploadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/**
 * 数据库图片存储表 服务实现类
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Service
@Slf4j
public class AImageServiceImpl extends ServiceImpl<AImageMapper, AImage> implements com.anzhen.service.AImageService {

    @Resource
    FileUploadService fileUploadService;
    @Resource
    MinioProperties properties;
    @Resource
    AImageMapper aImageMapper;
    @Resource
    AUserBackListService aUserBackListService;

    @Override
    public Page<AImage> mainView(Integer currentPage, Integer size) {
        AUserContext aUserContext = AUserContextHolder.getAUserContext();
        List<String> list = List.of();
        if (ObjectUtil.isNotNull(aUserContext) && ObjectUtil.isNotNull(aUserContext.getaUser())) {
            // 如果用户登录了 并且以及拉黑了图片的话 应该进行排除这些图片id
            QueryWrapper<AUserBackList> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", aUserContext.getaUser().getId());
            list = aUserBackListService.list(queryWrapper).stream().map(AUserBackList::getImageId)
                .collect(Collectors.toList());
        }
        Page<AImage> page = new Page<>(currentPage, size);
        return aImageMapper.mainView(page, CommonState.state, list);
    }

    @Override
    public List<AImage> findMainView() {
        return aImageMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadFileAndDb(MultipartFile multipartFile) {
        // 获取当前登录的用户
        AUser aUser = AUserContextHolder.getAUserContext().getaUser();
        // 如果用户没有登录则不进行上传
        if (ObjectUtil.isNull(aUser)) {
            throw new BadCredentialsException("用户未登录");
        }

        // 该文件应该用随机打乱的文件 不应该获取源文件大小
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String filePath = UUID.randomUUID() + suffix;
        AImage aImage = new AImage();
        aImage.setImageId(CommonState.PC_UPLOAD + System.currentTimeMillis());
        aImage.setImagePath(filePath);
        aImage.setCreatedBy(aUser.getId());
        aImage.setCreatedTime(LocalDateTime.now());
        // 上传文件到MinIO
        fileUploadService.fileUpload(properties.getBucket(), multipartFile, filePath);
        aImageMapper.insert(aImage);
    }

    @Override
    public void uploadFileAndDb(InputStream inputStream, String filePath, Integer objectSize) throws Exception {
        InputStreamCacher cache = new InputStreamCacher(inputStream);
        // 将流保存并二次使用
        fileUploadService.fileUpload(properties.getBucket(), filePath, cache.getInputStream(), objectSize);
        // 编写缩略图
        // 将文件设置进去数据库
        AImage aImage = new AImage();
        // 设置默认属性
        aImage.setImageId(CommonState.PC_UPLOAD + System.currentTimeMillis());
        aImage.setState(CommonState.state);
        aImage.setImagePath(filePath);
        aImage.setThumbnailImagePath(getThumbnail(cache.getInputStream(), filePath));
        aImageMapper.insert(aImage);
    }

    @Override
    public void uploadFileAndDb(InputStream inputStream, String filePath, Integer objectSize, InputStream inputStreamThumbnail,
                                String thumbnailPath, Integer thumbnailObjectSize) throws Exception {
        // 将流保存并二次使用
        fileUploadService.fileUpload(properties.getBucket(), filePath, inputStream, objectSize);
        // 编写缩略图
        // 将文件设置进去数据库
        AImage aImage = new AImage();
        // 设置默认属性
        aImage.setImageId(CommonState.PC_UPLOAD + System.currentTimeMillis());
        aImage.setState(CommonState.state);
        aImage.setImagePath(filePath);
        fileUploadService.fileUpload(properties.getBucket(), thumbnailPath, inputStreamThumbnail, thumbnailObjectSize);
        aImage.setThumbnailImagePath(thumbnailPath);
        aImageMapper.insert(aImage);
    }

    @Override
    public void delete(String id) {
        QueryWrapper<AImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        AImage aImage = aImageMapper.selectOne(queryWrapper);
        fileUploadService.delFile(properties.getBucket(), aImage.getImagePath());
        aImageMapper.deleteById(id);
    }

    /**
     * 获取缩略图路径
     *
     * @param inputStream 原图输入流
     */
    public String getThumbnail(InputStream inputStream, String filePath) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        BufferedImage originalImage = ImageIO.read(inputStream);
        BufferedImage thumbnail = Thumbnails.of(originalImage).scale(0.25).asBufferedImage();
        ImageIO.write(thumbnail, suffix, outputStream);
        InputStream thumbnaiInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String thumbnailPath = "thumbnail/" + UUID.randomUUID() + "." + suffix;
        fileUploadService.fileUpload(properties.getBucket(), thumbnailPath, thumbnaiInputStream,
            thumbnaiInputStream.available());
        return thumbnailPath;
    }
}
