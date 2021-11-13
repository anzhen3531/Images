package com.anzhen.service.impl;

import com.anzhen.entity.AImage;
import com.anzhen.mapper.AImageMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据库图片存储表 服务实现类
 * </p>
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Service
public class AImageService extends ServiceImpl<AImageMapper, AImage> implements com.anzhen.service.AImageService {

}
