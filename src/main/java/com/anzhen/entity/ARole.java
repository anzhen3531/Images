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
@ApiModel(value = "ARole", description = "用户表")
public class ARole {
    @TableId(type = IdType.AUTO)
    Integer id;
    /**
     * 状态
     */
    Integer state;

    /**
     * 角色名
     */
    String roleName;

    /**
     * 角色权限
     */
    String permission;
}
