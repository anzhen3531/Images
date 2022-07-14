package com.anzhen.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.common.context.AUserContextHolder;
import com.anzhen.common.result.CommonState;
import com.anzhen.config.minio.MinioProperties;
import com.anzhen.entity.AImage;
import com.anzhen.entity.AUser;
import com.anzhen.mapper.AImageMapper;
import com.anzhen.service.FileUploadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据库图片存储表 服务实现类
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Service
@Slf4j
public class AImageServiceImpl extends ServiceImpl<AImageMapper, AImage>
    implements com.anzhen.service.AImageService {

  @Resource FileUploadService fileUploadService;
  @Resource MinioProperties properties;
  @Resource AImageMapper aImageMapper;

  @Override
  public Page<AImage> mainView(Integer currentPage, Integer size) {
    Page<AImage> page = new Page<>(currentPage, size);
    Page<AImage> aImagePage = aImageMapper.mainView(page, CommonState.state);
    System.out.println(aImagePage);
    return aImageMapper.mainView(page, CommonState.state);
  }

  @Override
  public List<AImage> findMainView() {
    return aImageMapper.selectList(null);
  }

  @Override
  public void uploadFileAndDb(MultipartFile multipartFile) {
    // 获取当前登录的用户
    AUser aUser = AUserContextHolder.getAUserContext().getaUser();
    // 如果用户没有登录则不进行上传
    if (ObjectUtil.isNull(aUser)) {
      throw new BadCredentialsException("用户未登录");
    }
    // 上传文件到MinIO
    fileUploadService.fileUpload(properties.getBucket(), multipartFile);
    String fileUrl = multipartFile.getOriginalFilename();
    AImage aImage = new AImage();
    aImage.setImageId(CommonState.PC_UPLOAD + System.currentTimeMillis());
    aImage.setImagePath(fileUrl);
    aImage.setCreatedBy(aUser.getId());
    aImage.setCreatedTime(LocalDateTime.now());
    aImageMapper.insert(aImage);
  }

  @Override
  public void uploadFileAndDb(InputStream inputStream, String filePath, Integer objectSize)
      throws Exception {
    fileUploadService.fileUpload(properties.getBucket(), filePath, inputStream, objectSize);
    // 将文件设置进去数据库
    AImage aImage = new AImage();
    // 设置默认属性
    aImage.setImageId(CommonState.PC_UPLOAD + System.currentTimeMillis());
    aImage.setState(CommonState.state);
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
