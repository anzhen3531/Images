package com.anzhen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.anzhen.common.result.ApiResult;
import com.anzhen.entity.AImage;
import com.anzhen.jsoup.AdvanceImage;
import com.anzhen.service.AImageService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库图片存储表 前端控制器
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Slf4j
@Api(tags = "后端图片接口")
@RestController
@RequestMapping("/image")
public class AImageController {

    @Resource
    AImageService aImageService;
    @Resource
    AdvanceImage advanceImage;

    @ApiOperation("分页图片查询")
    @GetMapping("/main/view")
    public ApiResult<Page<AImage>> mainView(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return ApiResult.success(aImageService.mainView(page, size));
    }

    @ApiOperation("通过标签查询图片")
    @GetMapping("/tag")
    public ApiResult<List<AImage>> findImageByTag(@RequestParam("tag") String tag) {
        return ApiResult.success(aImageService.findImageByTag(tag));
    }

    @PutMapping("/{id}")
    @ApiOperation("删除图片")
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> audit(@PathVariable("id") String id) {
        // 先查询id是否存在
        aImageService.delete(id);
        return ApiResult.success();
    }

    @ApiOperation("审核图片")
    @DeleteMapping("/{id}")
    // 需要授予权限
    @PreAuthorize("hasRole('ROLE_IMAGE')")
    public ApiResult<Void> delete(@PathVariable("id") String id) {
        // 先查询id是否存在
        AImage image = aImageService.getById(id);
        if (ObjectUtil.isNotNull(image)) {
            // 设置状态为开启状态 可以封装成为参数
            image.setState(1);
            aImageService.updateById(image);
        }
        return ApiResult.success();
    }

    @ApiOperation("手动获取图片")
    @GetMapping("/manual/get/image")
    public ApiResult<Void> manualGetImage() throws Exception {
        advanceImage.getThumbnail("https://wallhaven.cc/hot?page=1");
        return ApiResult.success();
    }
}
