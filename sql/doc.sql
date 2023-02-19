# 图片表
CREATE TABLE `a_image`
(
    `id`                   varchar(20) NOT NULL,
    `image_id`             varchar(64)   DEFAULT NULL,
    `image_tag`            varchar(32)   DEFAULT NULL,
    `image_path`           varchar(1024) DEFAULT NULL,
    `thumbnail_image_path` varchar(1024) DEFAULT NULL COMMENT '缩略图地址',
    `state`                tinyint       DEFAULT NULL,
    `created_by`           bigint        DEFAULT NULL,
    `created_time`         datetime      DEFAULT CURRENT_TIMESTAMP,
    `update_by`            bigint        DEFAULT NULL,
    `update_time`          datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


# 角色表
CREATE TABLE `a_role`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `role_name`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `state`      tinyint                                               DEFAULT NULL,
    `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='角色表';

# 用户角色关联表
CREATE TABLE `a_role_user`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `role_id` int DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

# 用户表
CREATE TABLE `a_user`
(
    `id`           bigint                                                NOT NULL AUTO_INCREMENT,
    `username`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
    `password`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
    `name`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
    `email`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电子邮箱',
    `state`        tinyint                                               DEFAULT NULL COMMENT '逻辑删除',
    `created_time` datetime                                              DEFAULT NULL,
    `updated_time` datetime                                              DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

# 用户图片黑名单表
CREATE TABLE `a_user_back_list`
(
    `id`       int         NOT NULL AUTO_INCREMENT,
    `image_id` varchar(20) NOT NULL,
    `user_id`  bigint      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb3 COMMENT ='用户图片黑名单';

# 用户收藏表
CREATE TABLE `a_user_collection`
(
    `id`       int                             NOT NULL AUTO_INCREMENT,
    `image_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '图片id',
    `user_id`  bigint                          NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 28
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='用户收藏';

# oauth 客户信息表
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `resource_ids`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `client_secret`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `scope`                   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `authorized_grant_types`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `web_server_redirect_uri` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `authorities`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    `access_token_validity`   int                                                     DEFAULT NULL,
    `refresh_token_validity`  int                                                     DEFAULT NULL,
    `additional_information`  varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `autoapprove`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL,
    PRIMARY KEY (`client_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;


# 图片和标签关联表
CREATE TABLE `a_image_tag`
(
    `id`       BIGINT NOT NULL,
    `image_id` BIGINT NOT NULL,
    `tag_id`   BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);

# 标签表
CREATE TABLE `a_tag`
(
    ` id `       BIGINT      NOT NULL,
    ` tag_name ` VARCHAR(64) NOT NULL,
    ` state `    TINYINT(4) DEFAULT 1,
    PRIMARY KEY (` id `)
);


