-- 1. 协会基本信息表：首页展示协会简介、LOGO 等
CREATE TABLE `association_info` (
                                    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '协会ID',
                                    `name`         VARCHAR(100) NOT NULL COMMENT '协会名称',
                                    `description`  TEXT         COMMENT '协会简介',
                                    `logo`         VARCHAR(255) COMMENT '协会LOGO路径',
                                    `create_by`    BIGINT       NOT NULL COMMENT '创建人(sys_user.user_id)',
                                    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_by`    BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                    `update_time`  DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `del_flag`     CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志(0=正常,1=删除)',
                                    PRIMARY KEY (`id`),
                                    CONSTRAINT `fk_info_create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user`(`user_id`) ON DELETE RESTRICT,
                                    CONSTRAINT `fk_info_update_by` FOREIGN KEY (`update_by`) REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='协会基本信息表';

-- 2. 协会活动表：首页活动列表、详情
CREATE TABLE `association_activity` (
                                        `activity_id` BIGINT       NOT NULL AUTO_INCREMENT COMMENT '活动ID',
                                        `title`       VARCHAR(200) NOT NULL COMMENT '活动标题',
                                        `content`     TEXT         NOT NULL COMMENT '活动内容',
                                        `cover_img`   VARCHAR(255) DEFAULT NULL COMMENT '封面图路径',
                                        `start_time`  DATETIME     NOT NULL COMMENT '开始时间',
                                        `end_time`    DATETIME     NOT NULL COMMENT '结束时间',
                                        `create_by`   BIGINT       NOT NULL COMMENT '创建人(sys_user.user_id)',
                                        `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_by`   BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                        `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `del_flag`    CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志(0=正常,1=删除)',
                                        PRIMARY KEY (`activity_id`),
                                        INDEX `idx_activity_creator` (`create_by`),
                                        INDEX `idx_activity_period` (`start_time`,`end_time`),
                                        CONSTRAINT `fk_activity_create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user`(`user_id`) ON DELETE RESTRICT,
                                        CONSTRAINT `fk_activity_update_by` FOREIGN KEY (`update_by`) REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='协会活动表';

-- 3. 协会资讯表：首页资讯列表、详情
CREATE TABLE `association_news` (
                                    `news_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '资讯ID',
                                    `title`       VARCHAR(200) NOT NULL COMMENT '资讯标题',
                                    `content`     TEXT         NOT NULL COMMENT '资讯内容',
                                    `view_count`  INT          NOT NULL DEFAULT 0 COMMENT '浏览量',
                                    `create_by`   BIGINT       NOT NULL COMMENT '创建人(sys_user.user_id)',
                                    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_by`   BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `del_flag`    CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志(0=正常,1=删除)',
                                    PRIMARY KEY (`news_id`),
                                    INDEX `idx_news_creator` (`create_by`),
                                    CONSTRAINT `fk_news_create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user`(`user_id`) ON DELETE RESTRICT,
                                    CONSTRAINT `fk_news_update_by` FOREIGN KEY (`update_by`) REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='协会资讯表';

-- 4. 会员扩展资料表：个人空间资料
CREATE TABLE `association_profile` (
                                       `user_id`      BIGINT       NOT NULL COMMENT '对应sys_user.user_id',
                                       `student_id`   VARCHAR(20)  DEFAULT NULL COMMENT '学号',
                                       `join_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入会时间',
                                       `position`     VARCHAR(50)  DEFAULT NULL COMMENT '协会职务',
                                       `introduction` TEXT         COMMENT '个人介绍',
                                       `create_by`    BIGINT       DEFAULT NULL COMMENT '创建人(sys_user.user_id)',
                                       `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_by`    BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                       `update_time`  DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `del_flag`     CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志(0=正常,1=删除)',
                                       PRIMARY KEY (`user_id`),
                                       CONSTRAINT `fk_prof_user`
                                           FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                       CONSTRAINT `fk_prof_update_by`
                                           FOREIGN KEY (`update_by`) REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员扩展资料表';


-- 5. 标签定义表 + 用户-标签关联表：关注标签选择
CREATE TABLE `association_tag` (
                                   `tag_id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '标签ID',
                                   `name`        VARCHAR(50)  NOT NULL UNIQUE COMMENT '标签名称',
                                   `create_by`   BIGINT       NOT NULL COMMENT '创建人(sys_user.user_id)',
                                   `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_by`   BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                   `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `del_flag`    CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志(0=正常,1=删除)',
                                   PRIMARY KEY (`tag_id`),
                                   CONSTRAINT `fk_tag_create_by` FOREIGN KEY (`create_by`) REFERENCES `sys_user`(`user_id`) ON DELETE RESTRICT,
                                   CONSTRAINT `fk_tag_update_by` FOREIGN KEY (`update_by`) REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签定义表';

CREATE TABLE `association_user_tag` (
                                        `user_id`     BIGINT       NOT NULL COMMENT 'sys_user.user_id',
                                        `tag_id`      BIGINT       NOT NULL COMMENT 'association_tag.tag_id',
                                        PRIMARY KEY (`user_id`,`tag_id`),
                                        CONSTRAINT `fk_ut_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                        CONSTRAINT `fk_ut_tag`  FOREIGN KEY (`tag_id`)  REFERENCES `association_tag`(`tag_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员-标签关联表';

-- 6. 好友申请表 + 好友关系表：好友管理
CREATE TABLE `association_friend_request` (
                                              `request_id`  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '申请ID',
                                              `from_user`   BIGINT       NOT NULL COMMENT '发起者sys_user.user_id',
                                              `to_user`     BIGINT       NOT NULL COMMENT '接收者sys_user.user_id',
                                              `status`      ENUM('PENDING','ACCEPTED','REJECTED') NOT NULL DEFAULT 'PENDING' COMMENT '申请状态',
                                              `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
                                              `update_by`   BIGINT       DEFAULT NULL COMMENT '审核/更新人(sys_user.user_id)',
                                              `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                              PRIMARY KEY (`request_id`),
                                              CONSTRAINT `fk_fr_from`       FOREIGN KEY (`from_user`)   REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                              CONSTRAINT `fk_fr_to`         FOREIGN KEY (`to_user`)     REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                              CONSTRAINT `fk_fr_update_by`  FOREIGN KEY (`update_by`)   REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员好友申请表';

CREATE TABLE `association_friend` (
                                      `user_a`      BIGINT       NOT NULL COMMENT '好友一sys_user.user_id',
                                      `user_b`      BIGINT       NOT NULL COMMENT '好友二sys_user.user_id',
                                      `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '成为好友时间',
                                      PRIMARY KEY (`user_a`,`user_b`),
                                      CONSTRAINT `fk_f_user_a` FOREIGN KEY (`user_a`) REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                      CONSTRAINT `fk_f_user_b` FOREIGN KEY (`user_b`) REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员好友关系表';

-- 7. 日志（帖子）表：日志发布与可见性
CREATE TABLE `association_post` (
                                    `post_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                                    `author_id`   BIGINT       NOT NULL COMMENT '作者sys_user.user_id',
                                    `title`       VARCHAR(200) NOT NULL COMMENT '日志标题',
                                    `content`     TEXT         NOT NULL COMMENT '日志内容',
                                    `visibility`  ENUM('PRIVATE','FRIENDS_ONLY','PUBLIC') NOT NULL DEFAULT 'PUBLIC' COMMENT '可见范围',
                                    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                                    `update_by`   BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                    `update_time` DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `del_flag`    CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志(0=正常,1=删除)',
                                    PRIMARY KEY (`post_id`),
                                    INDEX `idx_post_user` (`author_id`),
                                    CONSTRAINT `fk_post_author`    FOREIGN KEY (`author_id`) REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                    CONSTRAINT `fk_post_update_by` FOREIGN KEY (`update_by`)  REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员日志/帖子表';

-- 8. 收藏表：资料收藏（可收藏活动、资讯、日志等）
CREATE TABLE `association_favorite` (
                                        `fav_id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
                                        `user_id`     BIGINT       NOT NULL COMMENT '收藏者sys_user.user_id',
                                        `target_type` ENUM('ACTIVITY','NEWS','POST') NOT NULL COMMENT '目标类型',
                                        `target_id`   BIGINT       NOT NULL COMMENT '目标ID',
                                        `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
                                        PRIMARY KEY (`fav_id`),
                                        INDEX `idx_fav_user` (`user_id`),
                                        CONSTRAINT `fk_fav_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员收藏表';

-- 9. 照片上传表：按活动分目录存储会员上传的照片
CREATE TABLE `association_photo` (
                                     `photo_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '照片ID',
                                     `user_id`      BIGINT       NOT NULL COMMENT '上传者sys_user.user_id',
                                     `activity_id`  BIGINT       DEFAULT NULL COMMENT '关联活动ID',
                                     `file_name`    VARCHAR(100) NOT NULL COMMENT '服务器存储文件名',
                                     `original_name`VARCHAR(255) NOT NULL COMMENT '原始文件名',
                                     `upload_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
                                     PRIMARY KEY (`photo_id`),
                                     INDEX `idx_photo_user`     (`user_id`),
                                     INDEX `idx_photo_activity` (`activity_id`),
                                     CONSTRAINT `fk_photo_user`     FOREIGN KEY (`user_id`)     REFERENCES `sys_user`(`user_id`) ON DELETE CASCADE,
                                     CONSTRAINT `fk_photo_activity` FOREIGN KEY (`activity_id`) REFERENCES `association_activity`(`activity_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员照片表';

-- 10. 入会申请表：游客/会员提交入会申请，由管理员审核
CREATE TABLE `association_application` (
                                           `app_id`       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '申请ID',
                                           `user_id`      BIGINT       DEFAULT NULL COMMENT '申请人sys_user.user_id（游客NULL）',
                                           `name`         VARCHAR(50)  NOT NULL COMMENT '申请人姓名',
                                           `contact`      VARCHAR(50)  NOT NULL COMMENT '联系方式',
                                           `reason`       TEXT         NOT NULL COMMENT '申请理由',
                                           `status`       ENUM('审核中','已通过','已拒绝') NOT NULL DEFAULT '审核中' COMMENT '审核状态',
                                           `reviewer_id`  BIGINT       DEFAULT NULL COMMENT '审核人sys_user.user_id',
                                           `review_time`  DATETIME     DEFAULT NULL COMMENT '审核时间',
                                           `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
                                           `update_by`    BIGINT       DEFAULT NULL COMMENT '更新人(sys_user.user_id)',
                                           `update_time`  DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           PRIMARY KEY (`app_id`),
                                           INDEX `idx_app_status` (`status`),
                                           CONSTRAINT `fk_app_user`       FOREIGN KEY (`user_id`)      REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL,
                                           CONSTRAINT `fk_app_reviewer`   FOREIGN KEY (`reviewer_id`)  REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL,
                                           CONSTRAINT `fk_app_update_by`  FOREIGN KEY (`update_by`)    REFERENCES `sys_user`(`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员入会申请表';
