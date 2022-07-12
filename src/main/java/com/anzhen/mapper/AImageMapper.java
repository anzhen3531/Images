package com.anzhen.mapper;

import com.anzhen.entity.AImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据库图片存储表 Mapper 接口
 *
 * @author anzhen
 * @since 2021-11-13
 */
public interface AImageMapper extends BaseMapper<AImage> {

  Page<AImage> mainView(Page<AImage> page, Integer state);
}
