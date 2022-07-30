package com.anzhen.service;

import com.anzhen.entity.AUserCollection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AUserCollectionService extends IService<AUserCollection> {

  /**
   * 通过用户id查询用户收藏的图片id
   *
   * @param id：用户id
   * @return
   */
  List<AUserCollection> findMineCollectionImage(Long id);
}
