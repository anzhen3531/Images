package com.anzhen.service.impl;

import com.anzhen.entity.AUser;
import com.anzhen.mapper.AUserMapper;
import com.anzhen.service.AUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AUserServiceImpl extends ServiceImpl<AUserMapper, AUser> implements AUserService {

    @Resource
    AUserMapper aUserMapper;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public AUser findByUsername(String username) {
        QueryWrapper<AUser> aUserQueryWrapper = new QueryWrapper<>();
        aUserQueryWrapper.eq("username", username);
        return aUserMapper.selectOne(aUserQueryWrapper);
    }

    @Override
    public void create(AUser aUser) {
        aUser.setPassword(passwordEncoder.encode("123456"));
        // 设置状态为默认开启状态
        aUser.setState(1);
        aUserMapper.insert(aUser);
    }
}
