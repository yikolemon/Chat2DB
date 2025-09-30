-- 环境表
CREATE TABLE IF NOT EXISTS `environment`
(
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   BIGINT(20) UNSIGNED NOT NULL COMMENT '创建人用户id',
    `modified_user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '修改人用户id',
    `name`             VARCHAR(128) NOT NULL COMMENT '环境名称',
    `short_name`       VARCHAR(128) DEFAULT NULL COMMENT '环境缩写',
    `color`            VARCHAR(32) DEFAULT NULL COMMENT '颜色',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库连接环境';

INSERT INTO `environment`(`id`, `create_user_id`, `modified_user_id`, `name`, `short_name`, `color`)
VALUES (1, 1, 1, 'Release Environment', 'RELEASE', 'RED'),
       (2, 1, 1, 'Test Environment', 'TEST', 'GREEN');

-- data_source 表修改
ALTER TABLE `data_source`
    ADD COLUMN `environment_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 2 COMMENT '环境id',
    MODIFY COLUMN `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '用户id',
    ADD COLUMN `kind` VARCHAR(32) NOT NULL DEFAULT 'PRIVATE' COMMENT '连接类型';

UPDATE `data_source` SET user_id = 1;

-- dbhub_user 表修改
ALTER TABLE `dbhub_user`
    ADD COLUMN `role_code` VARCHAR(32) DEFAULT NULL COMMENT '角色编码',
    ADD COLUMN `status` VARCHAR(32) NOT NULL DEFAULT 'VALID' COMMENT '用户状态',
    ADD COLUMN `create_user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '创建人用户id',
    ADD COLUMN `modified_user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '修改人用户id';

UPDATE `dbhub_user`
SET role_code = 'DESKTOP',
    user_name = '_desktop_default_user_name',
    password = '_desktop_default_user_name',
    nick_name = 'Desktop User'
WHERE id = 1;

INSERT INTO `dbhub_user` (user_name, password, nick_name, email, role_code)
VALUES ('chat2db', 'chat2db', 'Administrator', NULL, 'ADMIN');

CREATE UNIQUE INDEX uk_user_user_name ON `dbhub_user` (user_name);

-- team 表
CREATE TABLE IF NOT EXISTS `team`
(
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   BIGINT(20) UNSIGNED NOT NULL COMMENT '创建人用户id',
    `modified_user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '修改人用户id',
    `code`             VARCHAR(128) NOT NULL COMMENT '团队编码',
    `name`             VARCHAR(512) DEFAULT NULL COMMENT '团队名称',
    `status`           VARCHAR(32) NOT NULL DEFAULT 'VALID' COMMENT '团队状态',
    `description`      TEXT DEFAULT NULL COMMENT '团队描述',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队';

CREATE UNIQUE INDEX uk_team_code ON `team` (code);

-- team_user 表
CREATE TABLE IF NOT EXISTS `team_user`
(
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`   BIGINT(20) UNSIGNED NOT NULL COMMENT '创建人用户id',
    `modified_user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '修改人用户id',
    `team_id`          BIGINT(20) UNSIGNED NOT NULL COMMENT '团队id',
    `user_id`          BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户团队表';

CREATE INDEX idx_team_user_team_id ON `team_user` (`team_id`);
CREATE INDEX idx_team_user_user_id ON `team_user` (`user_id`);
CREATE UNIQUE INDEX uk_team_user ON `team_user` (`team_id`,`user_id`);

-- data_source_access 表
CREATE TABLE IF NOT EXISTS `data_source_access`
(
    `id`                 BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gmt_create`         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_id`     BIGINT(20) UNSIGNED NOT NULL COMMENT '创建人用户id',
    `modified_user_id`   BIGINT(20) UNSIGNED NOT NULL COMMENT '修改人用户id',
    `data_source_id`     BIGINT(20) UNSIGNED NOT NULL COMMENT '数据源id',
    `access_object_type` VARCHAR(32) NOT NULL COMMENT '授权类型',
    `access_object_id`   BIGINT(20) UNSIGNED NOT NULL COMMENT '授权id,根据类型区分是用户还是团队',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源授权';

CREATE INDEX idx_data_source_access_data_source_id ON `data_source_access` (`data_source_id`);
CREATE INDEX idx_data_source_access_access_object_id ON `data_source_access` (`access_object_type`, `access_object_id`);
CREATE UNIQUE INDEX uk_data_source_access ON `data_source_access` (`data_source_id`,`access_object_type`,`access_object_id`);

-- 其它表 user_id 默认值修改
ALTER TABLE `operation_saved` MODIFY COLUMN `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1;
UPDATE `operation_saved` SET user_id = 1;

ALTER TABLE `operation_log` MODIFY COLUMN `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1;
UPDATE `operation_log` SET user_id = 1;

ALTER TABLE `dashboard` MODIFY COLUMN `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1;
UPDATE `dashboard` SET user_id = 1;

ALTER TABLE `chart` MODIFY COLUMN `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT 1;
UPDATE `chart` SET user_id = 1;
