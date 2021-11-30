package com.anzhen.controller;


import com.anzhen.config.MinioProperties;
import com.anzhen.service.AImageService;
import com.anzhen.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 数据库图片存储表 前端控制器
 * </p>
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Api(tags = "后端图片接口")
@RestController
@RequestMapping("/a-image")
public class AImageController {

    @Resource
    AImageService aImageService;

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public String photoUpload(MultipartFile multipartFile) {
        aImageService.uploadFileAndDb(multipartFile);
        return "200 OK";
    }

    @ApiOperation("删除图片")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        // 先查询id是否存在
        aImageService.delete(id);
        return "200 OK";
    }
}
