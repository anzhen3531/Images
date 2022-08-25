package com.anzhen.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.RestController;

import com.anzhen.common.result.ApiResult;
import com.anzhen.common.result.CommonState;
import com.anzhen.entity.AImage;
import com.anzhen.service.AImageService;
import com.anzhen.service.FileUploadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.coobird.thumbnailator.Thumbnails;

@RestController
public class ImageAddThumbnail {

  private static final String BASE_URL = "http://120.26.48.34:9000/wallhaven-photo/";
  private static final String BUCKET_NAME = "wallhaven-photo";
  private static final String THUMBNAIL_NAME = "thumbnail/";
  @Resource private AImageService aimageService;
  @Resource
  private FileUploadService fileUploadService;

//  @GetMapping("/update/thumbnail")
  public ApiResult<Void> updateThumbnail() {
    // 获取全部的图片对象
    QueryWrapper<AImage> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("state", CommonState.state);
    queryWrapper.isNull("thumbnail_image_path");
    // 将图片截取为 300 * 200 个像素
    List<AImage> list = aimageService.list(queryWrapper);
    // 通过url获取到
    //    list = List.of(list.get(0));
    for (AImage image : list) {
      try {
        String path = image.getImagePath();
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        URL url = new URL(BASE_URL + path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 图片截取存在问题
        BufferedImage originalImage = ImageIO.read(url.openStream());
        BufferedImage thumbnail = Thumbnails.of(originalImage).scale(0.25).asBufferedImage();
        ImageIO.write(thumbnail, suffix, outputStream);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String thumbnailPath = THUMBNAIL_NAME + UUID.randomUUID() + "." + suffix;
        fileUploadService.fileUpload(
            BUCKET_NAME, thumbnailPath, inputStream, inputStream.available());
        // 修改数据库
        image.setThumbnailImagePath(thumbnailPath);
        image.setUpdateTime(LocalDateTime.now());
        aimageService.updateById(image);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return ApiResult.success();
  }
}
