package com.anzhen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 标签
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AImageTag", description = "图片和标签关联表")
public class ATag {
    @TableId(type = IdType.AUTO)
    Integer id;

    /**
     * 标签名称
     */
    String tagName;

    /**
     * 状态
     */
    Integer state;
}
