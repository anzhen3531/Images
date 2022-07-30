package com.anzhen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 用户黑名单列表 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AUserBackList", description = "用户黑名单表")
public class AUserBackList {
  @TableId(type = IdType.AUTO)
  Integer id;

  Long imageId;
  Long userId;
}
