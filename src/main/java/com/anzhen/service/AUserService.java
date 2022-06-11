package com.anzhen.service;

import com.anzhen.common.info.UserInfo;
import com.anzhen.entity.AUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface AUserService extends IService<AUser> {


    /**
     * 查找用户详情信息
     *
     * @param id
     * @return
     */
    UserInfo findInfoById(Long id);

    /**
     * 通过id查询用户
     *
     * @param id
     * @return
     */
    AUser findById(Long id);

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    AUser findByUsername(String username);

    /**
     * 创建用户
     *
     * @param aUser
     */
    void create(AUser aUser);

    /**
     * 修改用户和权限
     *
     * @param user
     * @param role
     * @return
     */
    Boolean updateAndPermission(AUser user, List<Integer> role);

    /**
     * 删除用户 软删除
     *
     * @param user
     */
    void delete(AUser user);

}
