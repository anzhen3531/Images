package com.anzhen.service;

import com.anzhen.entity.ARoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ARoleUserService extends IService<ARoleUser> {
    /**
     * 通过用户id查询角色和用户关联关系
     *
     * @param id
     * @return
     */
    List<ARoleUser> findRoleUserByUserId(Integer id);

    /**
     * 通过用户id删除角色和用户关联关系
     */
    void deleteByUserId(Integer id);
}
