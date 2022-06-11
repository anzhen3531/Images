package com.anzhen.service;

import com.anzhen.common.info.UserInfo;
import com.anzhen.entity.AUser;
import com.baomidou.mybatisplus.extension.service.IService;


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

    void create(AUser aUser);

    void delete(AUser user);

}
