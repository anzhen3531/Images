package com.anzhen.service.impl;

import com.anzhen.entity.ARoleUser;
import com.anzhen.mapper.ARoleUserMapper;
import com.anzhen.service.ARoleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ARoleUserServiceImpl extends ServiceImpl<ARoleUserMapper, ARoleUser> implements ARoleUserService {
}
