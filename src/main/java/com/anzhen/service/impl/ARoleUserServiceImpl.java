package com.anzhen.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.entity.ARoleUser;
import com.anzhen.mapper.ARoleUserMapper;
import com.anzhen.service.ARoleUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ARoleUserServiceImpl extends ServiceImpl<ARoleUserMapper, ARoleUser> implements ARoleUserService {
    @Resource
    ARoleUserMapper aroleUserMapper;

    @Override
    public List<ARoleUser> findRoleUserByUserId(Long id) {
        if (ObjectUtil.isNull(id)) {
            return List.of();
        }
        QueryWrapper<ARoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        return aroleUserMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByUserId(Long id) {
        QueryWrapper<ARoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        aroleUserMapper.delete(queryWrapper);
    }
}
