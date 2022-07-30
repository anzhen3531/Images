package com.anzhen.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.anzhen.entity.AUserCollection;
import com.anzhen.mapper.AUserCollectionMapper;
import com.anzhen.service.AUserCollectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class AUserCollectionServiceImpl extends ServiceImpl<AUserCollectionMapper, AUserCollection>
    implements AUserCollectionService {

  @Resource AUserCollectionMapper aUserCollectionMapper;

  @Override
  public List<AUserCollection> findMineCollectionImage(Long id) {
    if (ObjectUtil.isNull(id)) {
      return List.of();
    }
    QueryWrapper<AUserCollection> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", id);
    return aUserCollectionMapper.selectList(queryWrapper);
  }
}
