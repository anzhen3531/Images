package com.anzhen.controller;

import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.anzhen.common.exception.BadRequestException;
import com.anzhen.common.result.ApiResult;
import com.anzhen.controller.form.InitTaskParam;
import com.anzhen.service.AImageService;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 用户上传
 */
@RestController("/upload")
public class UploadFileController {

    @Autowired
    public AImageService aImageService;

    @Autowired
    public AmazonS3 amazonS3;

    @ApiOperation("图片上传")
    @PostMapping("/image")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> photoUpload(@RequestPart("file") MultipartFile file) {
        // 接收文件 进行上传
        if (ObjectUtil.isEmpty(file)) {
            throw new BadRequestException("文件为空");
        }
        aImageService.uploadFileAndDb(file);
        return ApiResult.success();
    }

    @ApiOperation("图片分片上传")
    @PostMapping("/fragment")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> fragmentPhoto(@Validate @RequestBody InitTaskParam param) {
        // 分片上传接口 核心API
        // amazonS3.initiateMultipartUpload();
        return ApiResult.success();
    }

    @ApiOperation("查看是否全部上传成功")
    @PostMapping("/validate/success")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> validateUploadFragmentSuccess() {
        // 查询上传完成的分片 核心 Api
        amazonS3.listMultipartUploads(null);
        return ApiResult.success();
    }

    @ApiOperation("上传完成之后合并分片")
    @PostMapping("complete/multipart")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> completeMultipartUpload() {
        // 合并分片 核心API
        amazonS3.completeMultipartUpload(null);
        return ApiResult.success();
    }
}
