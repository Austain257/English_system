-- 修复预设用户密码哈希问题
USE english_web;

-- 删除现有的admin和testuser
DELETE FROM users WHERE username IN ('admin', 'testuser');

-- 重新创建admin和testuser，使用正确的密码哈希
INSERT INTO users (username, password, nickname, email, role, status, create_time, update_time) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIch6OPOLPcKhKhUMk.H2zq3pK', '系统管理员', NULL, 'ADMIN', 1, NOW(), NOW()),
('testuser', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '测试用户', 'test@example.com', 'USER', 1, NOW(), NOW());

-- 验证插入结果
SELECT id, username, nickname, role, LENGTH(password) as password_length, LEFT(password, 20) as password_start 
FROM users 
WHERE username IN ('admin', 'testuser', 'newuser123') 
ORDER BY id;
