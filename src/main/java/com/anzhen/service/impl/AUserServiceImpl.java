package com.anzhen.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anzhen.common.info.UserInfo;
import com.anzhen.common.result.CommonState;
import com.anzhen.entity.ARole;
import com.anzhen.entity.ARoleUser;
import com.anzhen.entity.AUser;
import com.anzhen.mapper.AUserMapper;
import com.anzhen.service.ARoleService;
import com.anzhen.service.ARoleUserService;
import com.anzhen.service.AUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AUserServiceImpl extends ServiceImpl<AUserMapper, AUser> implements AUserService {

    @Resource
    AUserMapper aUserMapper;
    @Resource
    ARoleUserService aRoleUserService;
    @Resource
    ARoleService aRoleService;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public UserInfo findInfoById(Long id) {
        AUser aUser = aUserMapper.selectById(id);
        List<ARoleUser> aRoleUserList = aRoleUserService.findRoleUserByUserId(aUser.getId());
        List<Integer> roleIds = CollectionUtil.isEmpty(aRoleUserList) ?
                List.of() :
                aRoleUserList.stream().map(ARoleUser::getRoleId).toList();
        List<ARole> aRoles = CollectionUtil.isEmpty(roleIds) ?
                List.of() :
                aRoleService.listByIds(roleIds);
        UserInfo userInfo = new UserInfo();
        userInfo.setAUser(aUser);
        userInfo.setRoleList(aRoles);
        return userInfo;
    }

    @Override
    public AUser findById(Long id) {
        QueryWrapper<AUser> aUserQueryWrapper = new QueryWrapper<>();
        aUserQueryWrapper.eq("id", id);
        aUserQueryWrapper.eq("state", CommonState.state);
        return aUserMapper.selectOne(aUserQueryWrapper);
    }

    @Override
    public AUser findByUsername(String username) {
        QueryWrapper<AUser> aUserQueryWrapper = new QueryWrapper<>();
        aUserQueryWrapper.eq("username", username);
        aUserQueryWrapper.eq("state", CommonState.state);
        return aUserMapper.selectOne(aUserQueryWrapper);
    }

    @Override
    public void create(AUser aUser) {
        aUser.setPassword(passwordEncoder.encode("123456"));
        // 设置状态为默认开启状态
        aUser.setState(CommonState.state);
        aUserMapper.insert(aUser);
    }

    @Override
    public Boolean updateAndPermission(AUser user, List<Integer> roleIds) {
        // 先修改用户
        aUserMapper.updateById(user);
        // 删除之前的权限关联关系
        aRoleUserService.deleteByUserId(user.getId());
        // 创建用户和角色关联关系
        List<ARoleUser> aRoleUserList = roleIds.stream().map(roleId -> {
            ARoleUser aRoleUser = new ARoleUser();
            aRoleUser.setRoleId(roleId);
            aRoleUser.setUserId(user.getId());
            return aRoleUser;
        }).collect(Collectors.toList());
        // 创建用户权限
        aRoleUserService.saveBatch(aRoleUserList);
        return false;
    }

    @Override
    public void delete(AUser user) {
        aUserMapper.updateById(user);
    }
}
