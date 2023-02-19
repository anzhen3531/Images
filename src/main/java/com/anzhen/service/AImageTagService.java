package com.anzhen.service;

import com.anzhen.entity.AImage;
import com.anzhen.entity.AImageTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AImageTagService  extends IService<AImageTag> {
    List<String> findImageIdByTagId(Integer tagId);
}
