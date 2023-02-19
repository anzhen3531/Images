package com.anzhen.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anzhen.entity.ATag;
import com.anzhen.mapper.ATagMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ATagServiceImpl extends ServiceImpl<ATagMapper, ATag> implements com.anzhen.service.ATagService {
    @Resource
    ATagMapper aTagMapper;

    @Override
    public ATag findTagByName(String tag) {
        QueryWrapper<ATag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_name", tag);
        return aTagMapper.selectOne(queryWrapper);
    }
}
