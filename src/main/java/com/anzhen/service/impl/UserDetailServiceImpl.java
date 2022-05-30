package com.anzhen.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.entity.AUser;
import com.anzhen.service.AUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    AUserService aUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AUser aUser = aUserService.findByUsername(s);
        if (ObjectUtil.isNull(aUser) || !s.equals(aUser.getUsername())) {
            throw new UsernameNotFoundException("账号密码错误");
        }
        return new User(aUser.getUsername(), aUser.getPassword(), List.of(new SimpleGrantedAuthority("Role_Image")));
    }
}
