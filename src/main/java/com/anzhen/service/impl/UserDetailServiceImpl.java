package com.anzhen.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.anzhen.entity.ARole;
import com.anzhen.entity.ARoleUser;
import com.anzhen.entity.AUser;
import com.anzhen.service.ARoleService;
import com.anzhen.service.ARoleUserService;
import com.anzhen.service.AUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("UserDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private AUserService aUserService;
    @Resource
    private ARoleUserService aRoleUserService;
    @Resource
    private ARoleService aRoleService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AUser aUser = aUserService.findByUsername(s);
        if (ObjectUtil.isNull(aUser) || !s.equals(aUser.getUsername())) {
            throw new UsernameNotFoundException("账号密码错误");
        }

        // 查找权限
        List<ARoleUser> aRoleUserList = aRoleUserService.listByIds(List.of(aUser.getId()));
        List<ARole> aRoles = List.of();
        if (CollectionUtil.isNotEmpty(aRoleUserList)) {
            List<Integer> aRoleIds = aRoleUserList.stream().map(ARoleUser::getRoleId).collect(Collectors.toList());
            aRoles = aRoleService.listByIds(aRoleIds);
        }

        // 查找权限
        List<GrantedAuthority> permissionList = CollectionUtil.isEmpty(aRoles) ? List.of() : aRoles.stream().map(aRole -> new SimpleGrantedAuthority(aRole.getPermission())).collect(Collectors.toList());
        return new User(aUser.getUsername(), aUser.getPassword(), permissionList);
    }
}
