package com.anzhen.controller;


import com.anzhen.jsoup.JobImage;
import com.anzhen.service.AImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    JobImage jobImage;

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public String photoUpload() throws Exception {
        jobImage.execute();
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
