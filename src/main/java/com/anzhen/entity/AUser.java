package com.anzhen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AUser", description = "用户表")
public class AUser {

  @TableId(type = IdType.AUTO)
  Long id;

  String username;
  String password;
  String name;
  String email;
  Integer state;
  LocalDateTime created_time;
  LocalDateTime updated_time;
}
