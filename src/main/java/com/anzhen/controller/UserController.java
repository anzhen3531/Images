package com.anzhen.controller;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.common.form.UserForm;
import com.anzhen.common.form.UserUpdateForm;
import com.anzhen.common.info.UserInfo;
import com.anzhen.common.result.ApiResult;
import com.anzhen.common.result.ApiResultCode;
import com.anzhen.entity.AUser;
import com.anzhen.service.AUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private AUserService aUserService;

    /**
     * 查询用户详情
     *
     * @return
     */
    @GetMapping("/info/{id}")
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
     * @return
     */
    @PostMapping("")
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
        user.setPassword(userForm.getPassword());
        user.setName(userForm.getUsername());
        aUserService.create(user);
        return ApiResult.success();
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/{id}")
    public ApiResult<Void> update(@RequestBody UserUpdateForm userUpdateForm,
                                  @PathVariable("id") Long id) {
        AUser user = aUserService.findById(id);
        if (ObjectUtil.isNull(user)) {
            return ApiResult.failed(ApiResultCode.USER_NOT_EXIST);
        }
        // 修改用户名
        aUserService.update();
        // 修改权限  可以通过 之前删除之前关联的信息  删除之后重新插入
        // 附带修改权限
        return ApiResult.success();
    }

    /**
     * 删除用户
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable("id") Long id) {
        // 对用户进行归档
        AUser user = aUserService.findById(id);
        if (ObjectUtil.isNull(user)) {
            return ApiResult.failed(ApiResultCode.USER_NOT_EXIST);
        }
        aUserService.delete(user);
        return ApiResult.success();
    }
}
