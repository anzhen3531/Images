package com.anzhen.service;

import com.anzhen.entity.AImage;
import com.anzhen.entity.ATag;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ATagService  extends IService<ATag> {
    ATag findTagByName(String tag);
}
