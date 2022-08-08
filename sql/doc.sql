/**
  创建数据库
 */
CREATE DATABASE `imageprodect`;

/**
    创建表
 */
CREATE TABLE `a_image`
(
    `id`                   bigint unsigned NOT NULL AUTO_INCREMENT,
    `image_id`             varchar(64)   DEFAULT NULL,
    `image_tag`            varchar(32)   DEFAULT NULL,
    `image_path`           varchar(1024) DEFAULT NULL,
    `thumbnail_image_path` varchar(1024) DEFAULT NULL,
    `state`                tinyint       DEFAULT NULL,
    `created_by`           bigint        DEFAULT NULL,
    `created_time`         datetime      DEFAULT CURRENT_TIMESTAMP,
    `update_by`            bigint        DEFAULT NULL,
    `update_time`          datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1523108141249638402
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='数据库图片存储表';

/**
  用户表
 */

CREATE TABLE `a_user`
(
    `id`           INT         NOT NULL AUTO_INCREMENT,
    `username`     VARCHAR(32) NOT NULL COMMENT '用户名',
    `password`     VARCHAR(32) NOT NULL COMMENT '密码',
    `name`         VARCHAR(32) NULL COMMENT '名字',
    `email`        VARCHAR(32) NULL COMMENT '电子邮箱',
    `state`        TINYINT(4)  NULL COMMENT '逻辑删除',
    `created_time` DATETIME    NULL,
    `updated_time` DATETIME    NULL,
    PRIMARY KEY (`id`)
);



CREATE TABLE `a_role_user`
(
    `id`      INT NOT NULL AUTO_INCREMENT,
    `role_id` INT NULL,
    `user_id` INT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `a_role`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `role_name`  VARCHAR(32) NULL,
    `permission` VARCHAR(32) NULL,
    `state`      TINYINT     NULL,
    PRIMARY KEY (`id`)
)
    COMMENT = '角色表';



INSERT INTO `a_role` (`id`, `role_name`, `state`, `permission`)
VALUES ('1', '图片管理员', '1', 'ROLE_IMAGE');
INSERT INTO `a_role` (`id`, `role_name`, `state`, `permission`)
VALUES ('2', '用户管理员', '1', 'ROLE_USER');


# 创建用户收藏图片关联表
CREATE TABLE `a_user_collection`
(
    `id`       int    NOT NULL AUTO_INCREMENT,
    `image_id` BIGINT NOT NULL COMMENT '图片id',
    `user_id`  BIGINT NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='用户收藏';


CREATE TABLE `a_user_back_list`
(
    `id`       INT    NOT NULL AUTO_INCREMENT,
    `image_id` BIGINT NOT NULL,
    `user_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`)
)
    COMMENT = '用户图片黑名单';




