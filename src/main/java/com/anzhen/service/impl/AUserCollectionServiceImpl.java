package com.anzhen.service.impl;

import com.anzhen.entity.AUserCollection;
import com.anzhen.mapper.AUserCollectionMapper;
import com.anzhen.service.AUserCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AUserCollectionServiceImpl extends ServiceImpl<AUserCollectionMapper, AUserCollection>
    implements AUserCollectionService {}
