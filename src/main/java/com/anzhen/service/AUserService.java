package com.anzhen.service;

import com.anzhen.entity.AUser;
import com.baomidou.mybatisplus.extension.service.IService;


public interface AUserService extends IService<AUser> {

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    AUser findByUsername(String username);

    void create(AUser aUser);
}
