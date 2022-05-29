
/**
  创建数据库
 */
CREATE DATABASE `imageprodect`;

/**
    创建表
 */
CREATE TABLE `a_image` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                           `image_id` varchar(64) DEFAULT NULL,
                           `image_tag` varchar(32) DEFAULT NULL,
                           `image_path` varchar(1024) DEFAULT NULL,
                           `state` tinyint DEFAULT NULL,
                           `created_by` bigint DEFAULT NULL,
                           `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
                           `update_by` bigint DEFAULT NULL,
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1523108141249638402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据库图片存储表';

/**
  用户表
 */

CREATE TABLE `imageprodect`.`a_user` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `username` VARCHAR(32) NOT NULL COMMENT '用户名',
                                       `password` VARCHAR(32) NOT NULL COMMENT '密码',
                                       `name` VARCHAR(32) NULL COMMENT '名字',
                                       `email` VARCHAR(32) NULL COMMENT '电子邮箱',
                                       `state` TINYINT(4) NULL COMMENT '逻辑删除',
                                       `created_time` DATETIME NULL,
                                       `updated_time` DATETIME NULL,
                                       PRIMARY KEY (`id`));

