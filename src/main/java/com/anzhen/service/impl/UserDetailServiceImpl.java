package com.anzhen.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 通过name查询用户是否存在
        System.out.println(s);
        // 不存在则抛出异常
        // 对比密码
        // 存在则进行设置对象
        // 设置权限之后进行返回
        return new User("anzhen", passwordEncoder.encode("123456"), List.of());
    }
}
