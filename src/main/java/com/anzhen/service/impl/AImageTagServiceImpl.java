package com.anzhen.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anzhen.entity.AImageTag;
import com.anzhen.mapper.AImageTagMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class AImageTagServiceImpl extends ServiceImpl<AImageTagMapper, AImageTag>
    implements com.anzhen.service.AImageTagService {
    @Resource
    AImageTagMapper aImageTagMapper;

    @Override
    public List<String> findImageIdByTagId(Integer tagId) {
        QueryWrapper<AImageTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_id", tagId);
        List<AImageTag> aImageTags = aImageTagMapper.selectList(queryWrapper);
        return aImageTags.stream().map(AImageTag::getImageId).collect(Collectors.toList());
    }
}
