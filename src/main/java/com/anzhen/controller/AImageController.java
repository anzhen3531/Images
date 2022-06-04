package com.anzhen.controller;


import cn.hutool.core.util.ObjectUtil;
import com.anzhen.common.result.ApiResult;
import com.anzhen.entity.AImage;
import com.anzhen.jsoup.JobImage;
import com.anzhen.service.AImageService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/image")
public class AImageController {

    @Resource
    AImageService aImageService;
    @Resource
    JobImage jobImage;

    @ApiOperation("分页图片查询")
    @GetMapping("/main/view")
    public ApiResult<Page<AImage>> mainView(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return ApiResult.success(aImageService.mainView(page, size));
    }

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> photoUpload() throws Exception {
        jobImage.execute();
        return ApiResult.success();
    }

    @ApiOperation("删除图片")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> audit(@PathVariable("id") Long id) {
        // 先查询id是否存在
        aImageService.delete(id);
        return ApiResult.success();
    }

    @ApiOperation("审核图片")
    @DeleteMapping("/{id}")
    // 需要授予权限
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> delete(@PathVariable("id") Long id) {
        // 先查询id是否存在
        AImage image = aImageService.getById(id);
        if (ObjectUtil.isNotNull(image)) {
            // 设置状态为开启状态  可以封装成为参数
            image.setState(1);
            aImageService.updateById(image);
        }
        return ApiResult.success();
    }
}
