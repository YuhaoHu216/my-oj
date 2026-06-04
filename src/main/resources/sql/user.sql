-- 用户表
CREATE DATABASE IF NOT EXISTS my_oj CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE my_oj;

CREATE TABLE if not exists user (
                        `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `username` VARCHAR(50) NOT NULL COMMENT '用户名',
                        `password` VARCHAR(100) NOT NULL COMMENT '密码',
                        `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
                        `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                        `profile` TEXT DEFAULT NULL COMMENT '个人简介',
                        `role` VARCHAR(20) DEFAULT 'user' COMMENT '角色：admin/user',
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用, 1-启用',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_username` (`username`),
                        UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
