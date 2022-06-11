package com.anzhen.service.impl;

import com.anzhen.common.result.CommonState;
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
    public List<ARoleUser> findRoleUserByUserId(Integer id) {
        QueryWrapper<ARoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        queryWrapper.eq("state", CommonState.state);
        return aroleUserMapper.selectList(queryWrapper);
    }
}
