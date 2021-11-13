package com.anzhen.controller;


import com.anzhen.config.MinioProperties;
import com.anzhen.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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
    FileUploadService fileUploadService;
    @Resource
    MinioProperties minioProperties;

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public String photoUpload(MultipartFile multipartFile) {
        fileUploadService.fileUpload(minioProperties.getBucket(), multipartFile);
        return "200 OK";
    }


    @ApiOperation("测试接口")
    @GetMapping("/hello")
    public String HelloWord() {
        return "hello World";
    }

}
