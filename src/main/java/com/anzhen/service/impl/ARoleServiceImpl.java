package com.anzhen.service.impl;

import com.anzhen.entity.ARole;
import com.anzhen.mapper.ARoleMapper;
import com.anzhen.service.ARoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ARoleServiceImpl extends ServiceImpl<ARoleMapper, ARole> implements ARoleService {
}
