package com.anzhen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AROLEUSER", description = "用户表")
public class ARoleUser {

  @TableId(type = IdType.AUTO)
  Long id;

  /** 用户id */
  Long userId;

  /** 角色id */
  Long roleId;
}
