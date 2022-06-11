package com.anzhen.service;

import com.anzhen.entity.ARoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ARoleUserService extends IService<ARoleUser> {
    List<ARoleUser> findRoleUserByUserId(Integer id);
}
