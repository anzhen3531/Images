package com.anzhen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * AImageTag
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AImageTag", description = "图片和标签关联表")
public class AImageTag {
    @TableId(type = IdType.AUTO)
    Integer id;
    /** 状态 */
    String imageId;
    /** 角色名 */
    Integer tadId;
}
