-- ========================================
-- 英语学习系统用户认证数据库初始化脚本
-- 创建时间: 2025-12-23
-- 描述: 为现有英语学习系统添加用户认证功能
-- ========================================

USE english_web;

-- ========================================
-- 1. 创建用户表
-- ========================================

-- 关闭外键约束检查
SET FOREIGN_KEY_CHECKS = 0;

-- 创建用户表
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(300) NOT NULL COMMENT '密码(加密)',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) DEFAULT 'https://austain-java-ai-web.oss-cn-beijing.aliyuncs.com/chenhaoxing.jpg' COMMENT '头像URL',
  `role` enum('USER', 'ADMIN') NOT NULL DEFAULT 'USER' COMMENT '用户角色',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 1:正常 0:禁用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_email` (`email`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- 重新启用外键检查（非常重要！）
SET FOREIGN_KEY_CHECKS = 1;

-- 创建用户会话表
DROP TABLE IF EXISTS `user_sessions`;
CREATE TABLE `user_sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `session_token` varchar(255) NOT NULL COMMENT '会话令牌',
  `expires_at` datetime NOT NULL COMMENT '过期时间',
  `ip_address` varchar(45) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_session_token` (`session_token`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_expires_at` (`expires_at`),
  CONSTRAINT `fk_sessions_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户会话表';

-- ========================================
-- 2. 修改现有表结构，添加用户关联
-- ========================================

-- 为 againenglishword 表添加用户关联
ALTER TABLE `againenglishword` 
ADD COLUMN `user_id` bigint DEFAULT NULL COMMENT '用户ID' AFTER `id`,
ADD KEY `idx_againenglishword_user_id` (`user_id`),
ADD CONSTRAINT `fk_againenglishword_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

-- 为 daily_record 表添加用户关联
ALTER TABLE `daily_record`
ADD COLUMN `user_id` bigint DEFAULT NULL COMMENT '用户ID' AFTER `id`,
ADD KEY `idx_daily_record_user_id` (`user_id`),
ADD CONSTRAINT `fk_daily_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

-- 为 study_record 表添加用户关联
ALTER TABLE `study_record`
ADD COLUMN `user_id` bigint DEFAULT NULL COMMENT '用户ID' AFTER `id`,
ADD KEY `idx_study_record_user_id` (`user_id`),
ADD CONSTRAINT `fk_study_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

-- 为 word_record 表添加用户关联
ALTER TABLE `word_record`
ADD COLUMN `user_id` bigint DEFAULT NULL COMMENT '用户ID' AFTER `id`,
ADD KEY `idx_word_record_user_id` (`user_id`),
ADD CONSTRAINT `fk_word_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

-- ========================================
-- 3. 创建默认管理员账户
-- ========================================

-- 插入默认管理员账户
-- 用户名: admin
-- 密码: admin123 (BCrypt加密后的密码)
INSERT INTO `users` (`username`, `password`, `nickname`, `role`, `status`) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIch6OPOLPcKhKhUMk.H2zq3pK', '系统管理员', 'ADMIN', 1);

-- 插入测试用户账户
-- 用户名: testuser
-- 密码: test123
INSERT INTO `users` (`username`, `password`, `nickname`, `email`, `role`, `status`) VALUES 
('testuser', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '测试用户', 'test@example.com', 'USER', 1);

-- ========================================
-- 4. 创建用于数据迁移的临时存储过程
-- ========================================

DELIMITER //

-- 创建存储过程：将现有数据关联到默认管理员账户
CREATE PROCEDURE MigrateExistingDataToAdmin()
BEGIN
    DECLARE admin_user_id BIGINT;
    
    -- 获取管理员用户ID
    SELECT id INTO admin_user_id FROM users WHERE username = 'admin' LIMIT 1;
    
    -- 更新现有的 againenglishword 记录
    UPDATE againenglishword SET user_id = admin_user_id WHERE user_id IS NULL;
    
    -- 更新现有的 daily_record 记录
    UPDATE daily_record SET user_id = admin_user_id WHERE user_id IS NULL;
    
    -- 更新现有的 study_record 记录
    UPDATE study_record SET user_id = admin_user_id WHERE user_id IS NULL;
    
    -- 更新现有的 word_record 记录
    UPDATE word_record SET user_id = admin_user_id WHERE user_id IS NULL;
    
    SELECT 'Data migration completed successfully!' as message;
END //

DELIMITER ;

-- 执行数据迁移
CALL MigrateExistingDataToAdmin();

-- 删除临时存储过程
DROP PROCEDURE IF EXISTS MigrateExistingDataToAdmin;

-- ========================================
-- 5. 创建有用的视图和索引优化
-- ========================================

-- 删除现有视图
DROP VIEW IF EXISTS `english_web`.`user_statistics`;


-- 创建用户统计视图
CREATE OR REPLACE VIEW `user_statistics` AS
SELECT 
    u.id as user_id,
    u.username,
    u.nickname,
    u.role,
    u.create_time as user_create_time,
    u.last_login_time,
    COALESCE(wrong_words.count, 0) as wrong_words_count,
    COALESCE(study_records.count, 0) as study_records_count,
    COALESCE(daily_records.count, 0) as daily_records_count,
    COALESCE(word_records.count, 0) as word_records_count
FROM users u
LEFT JOIN (
    SELECT user_id, COUNT(*) as count 
    FROM againenglishword 
    GROUP BY user_id
) wrong_words ON u.id = wrong_words.user_id
LEFT JOIN (
    SELECT user_id, COUNT(*) as count 
    FROM study_record 
    GROUP BY user_id
) study_records ON u.id = study_records.user_id
LEFT JOIN (
    SELECT user_id, COUNT(*) as count 
    FROM daily_record 
    GROUP BY user_id
) daily_records ON u.id = daily_records.user_id
LEFT JOIN (
    SELECT user_id, COUNT(*) as count 
    FROM word_record 
    GROUP BY user_id
) word_records ON u.id = word_records.user_id;

-- ========================================
-- 6. 插入示例数据（可选）
-- ========================================

-- 为测试用户创建一些示例错词数据
INSERT INTO `againenglishword` (`user_id`, `word`, `chinese`, `pronounce`, `times`, `bookname`) 
SELECT 
    (SELECT id FROM users WHERE username = 'testuser'),
    'example',
    '例子，实例',
    '/ɪɡˈzæmpl/',
    1,
    'test_book'
WHERE NOT EXISTS (
    SELECT 1 FROM againenglishword 
    WHERE word = 'example' 
    AND user_id = (SELECT id FROM users WHERE username = 'testuser')
);

INSERT INTO `againenglishword` (`user_id`, `word`, `chinese`, `pronounce`, `times`, `bookname`) 
SELECT 
    (SELECT id FROM users WHERE username = 'testuser'),
    'difficult',
    '困难的，艰难的',
    '/ˈdɪfɪkəlt/',
    2,
    'test_book'
WHERE NOT EXISTS (
    SELECT 1 FROM againenglishword 
    WHERE word = 'difficult' 
    AND user_id = (SELECT id FROM users WHERE username = 'testuser')
);

-- ========================================
-- 7. 清理过期会话的定时任务（可选）
-- ========================================

-- 创建清理过期会话的存储过程
DELIMITER //
CREATE PROCEDURE CleanExpiredSessions()
BEGIN
    DELETE FROM user_sessions WHERE expires_at < NOW();
    SELECT ROW_COUNT() as cleaned_sessions_count;
END //
DELIMITER ;

-- ========================================
-- 8. 验证安装
-- ========================================

-- 检查表是否创建成功
SELECT 
    'users' as table_name, 
    COUNT(*) as record_count 
FROM users
UNION ALL
SELECT 
    'user_sessions' as table_name, 
    COUNT(*) as record_count 
FROM user_sessions
UNION ALL
SELECT 
    'user_statistics_view' as table_name,
    COUNT(*) as record_count 
FROM user_statistics;

-- 显示用户统计信息
SELECT * FROM user_statistics;

-- ========================================
-- 初始化完成提示
-- ========================================

SELECT CONCAT(
    '🎉 数据库初始化完成！\n',
    '默认管理员账户:\n',
    '  用户名: admin\n',
    '  密码: admin123\n\n',
    '测试用户账户:\n',
    '  用户名: testuser\n', 
    '  密码: test123\n\n',
    '请及时修改默认密码！'
) as '初始化结果';
