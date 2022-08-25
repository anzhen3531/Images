package com.anzhen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.jsoup.helper.StringUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.anzhen.common.context.AUserContext;
import com.anzhen.common.context.AUserContextHolder;
import com.anzhen.common.form.UserForm;
import com.anzhen.common.form.UserUpdateForm;
import com.anzhen.common.info.UserInfo;
import com.anzhen.common.result.ApiResult;
import com.anzhen.common.result.ApiResultCode;
import com.anzhen.entity.AUser;
import com.anzhen.entity.AUserBackList;
import com.anzhen.entity.AUserCollection;
import com.anzhen.service.AUserBackListService;
import com.anzhen.service.AUserCollectionService;
import com.anzhen.service.AUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private AUserService aUserService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AUserCollectionService aUserCollectionService;
    @Resource
    private AUserBackListService aUserBackListService;

    @GetMapping("/info/mine")
    @ApiOperation("查询用户详情")
    public ApiResult<UserInfo> findMineInfo() {
        log.info("进入查询自己详细信息接口");
        AUserContext aUserContext = AUserContextHolder.getAUserContext();
        if (ObjectUtil.isNull(aUserContext)) {
            return ApiResult.failed("未登录");
        }
        AUser aUser = aUserContext.getaUser();
        if (ObjectUtil.isNull(aUser)) {
            return ApiResult.failed("未找到该用户");
        } else {
            UserInfo userInfo = aUserService.findInfoById(aUser.getId());
            if (ObjectUtil.isEmpty(userInfo)) {
                return ApiResult.failed(ApiResultCode.USER_NOT_EXIST);
            }
            return ApiResult.success(userInfo);
        }
    }

    @GetMapping("/collection/mine")
    @ApiOperation("通过用户id查询用户收藏图片")
    public ApiResult<List<AUserCollection>> findUserCollectionImage() {
        AUserContext aUserContext = AUserContextHolder.getAUserContext();
        if (ObjectUtil.isNull(aUserContext)) {
            return ApiResult.failed("未登录");
        }
        AUser aUser = aUserContext.getaUser();
        if (ObjectUtil.isNull(aUser)) {
            return ApiResult.failed("未找到该用户");
        } else {
            List<AUserCollection> collectionImage = aUserCollectionService.findMineCollectionImage(aUser.getId());
            return ApiResult.success(collectionImage);
        }
    }

    /**
     * 查询用户详情
     *
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询用户详情")
    public ApiResult<UserInfo> findUserInfo(@PathVariable("id") Long id) {
        UserInfo userInfo = aUserService.findInfoById(id);
        if (ObjectUtil.isEmpty(userInfo)) {
            return ApiResult.failed(ApiResultCode.USER_NOT_EXIST);
        }
        return ApiResult.success(userInfo);
    }

    /**
     * 用户注册
     *
     * @param userForm 用户创建表单
     */
    @PostMapping("")
    @ApiOperation("用户注册")
    public ApiResult<Void> create(@RequestBody UserForm userForm) {
        // 验证是否存在相同的用户名
        String username = userForm.getUsername();
        AUser user = aUserService.findByUsername(username);
        if (ObjectUtil.isNotNull(user)) {
            // 抛出异常
            return ApiResult.failed(ApiResultCode.USER_ACCOUNT_REPEAT);
        }
        user = new AUser();
        user.setUsername(userForm.getUsername());
        // 设置加密密码
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setName(userForm.getUsername());
        aUserService.create(user);
        return ApiResult.success();
    }

    /**
     * 用户收藏图片
     *
     * @param imageId 图片id
     */
    @PostMapping("/collection/{imageId}")
    @ApiOperation("用户收藏图片")
    public ApiResult<Void> collectionImage(@PathVariable String imageId) {
        AUserContext aUserContext = AUserContextHolder.getAUserContext();
        if (ObjectUtil.isNull(aUserContext)) {
            return ApiResult.failed("未登录");
        }
        AUser aUser = aUserContext.getaUser();
        if (ObjectUtil.isNull(aUser)) {
            return ApiResult.failed("未找到该用户");
        } else {
            AUserCollection aUserCollection = new AUserCollection();
            aUserCollection.setUserId(aUser.getId());
            aUserCollection.setImageId(imageId);
            aUserCollectionService.save(aUserCollection);
            return ApiResult.success();
        }
    }

    /**
     * 用户添加图片黑名单
     *
     * @param imageId 图片id
     */
    @PostMapping("/back/list/{imageId}")
    @ApiOperation("用户添加图片黑名单")
    public ApiResult<Void> backListImage(@PathVariable String imageId) {
        AUserContext aUserContext = AUserContextHolder.getAUserContext();
        if (ObjectUtil.isNull(aUserContext)) {
            return ApiResult.failed("未登录");
        }
        AUser aUser = aUserContext.getaUser();
        if (ObjectUtil.isNull(aUser)) {
            return ApiResult.failed("未找到该用户");
        } else {
            AUserBackList aUserBackList = new AUserBackList();
            aUserBackList.setUserId(aUser.getId());
            aUserBackList.setImageId(imageId);
            aUserBackListService.save(aUserBackList);
            return ApiResult.success();
        }
    }

    /** 修改用户信息 */
    @PutMapping("/{id}")
    @ApiOperation("修改用户信息")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResult<Void> update(@RequestBody UserUpdateForm userUpdateForm, @PathVariable("id") Long id) {
        AUser user = aUserService.findById(id);
        if (ObjectUtil.isNull(user)) {
            return ApiResult.failed(ApiResultCode.USER_NOT_EXIST);
        }
        // 判断传递的是否是空值
        if (!StringUtil.isBlank(userUpdateForm.getUsername())) {
            user.setName(userUpdateForm.getUsername());
        }
        if (!StringUtil.isBlank(userUpdateForm.getPassword())) {
            user.setPassword(userUpdateForm.getPassword());
        }

        boolean flag = CollectionUtil.isEmpty(userUpdateForm.getRole()) ? aUserService.updateById(user)
            : aUserService.updateAndPermission(user, userUpdateForm.getRole());
        if (flag) {
            return ApiResult.success();
        } else {
            return ApiResult.failed("修改失败");
        }
    }

    /**
     * 删除用户
     *
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResult<Void> delete(@PathVariable("id") Long id) {
        // 对用户进行归档
        AUser user = aUserService.findById(id);
        if (ObjectUtil.isNull(user)) {
            return ApiResult.failed(ApiResultCode.USER_NOT_EXIST);
        }
        aUserService.delete(user);
        return ApiResult.success();
    }

    /**
     * 删除用户
     *
     */
    @DeleteMapping("/collection/{imageId}")
    @ApiOperation("取消收藏")
    public ApiResult<Void> cancelCollection(@PathVariable("imageId") String imageId) {
        AUserContext aUserContext = AUserContextHolder.getAUserContext();
        if (ObjectUtil.isNull(aUserContext)) {
            return ApiResult.failed("未登录");
        }
        AUser aUser = aUserContext.getaUser();
        if (ObjectUtil.isNull(aUser)) {
            return ApiResult.failed("未找到该用户");
        } else {
            QueryWrapper<AUserCollection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("image_id", imageId);
            queryWrapper.eq("user_id", aUser.getId());
            aUserCollectionService.remove(queryWrapper);
            return ApiResult.success();
        }
    }
}
