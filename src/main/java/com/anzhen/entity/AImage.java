package com.anzhen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库图片存储表
 *
 * @author anzhen
 * @since 2021-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AImage对象", description = "数据库图片存储表")
public class AImage implements Serializable {

  @TableId private Long id;

  private String imageId;

  private String imageTag;

  private String imagePath;

  private Integer state;

  private Long createdBy;

  private LocalDateTime createdTime;

  private Long updateBy;

  private LocalDateTime updateTime;
}
