# 英语学习系统用户认证功能部署指南

## 📋 概述

本文档详细说明了如何为现有的英语学习系统添加完整的用户认证功能，包括用户注册、登录、权限控制、个人中心等功能。

## 🚀 系统特性

### ✨ 核心功能
- **用户注册与登录** - 支持用户名/邮箱注册，安全登录
- **权限管理** - 管理员和普通用户角色区分
- **个人中心** - 用户信息管理、头像上传、密码修改
- **智能错词本** - 个人错词记录，支持权限隔离
- **会话管理** - JWT Token会话管理，自动过期清理
- **响应式UI** - 现代化界面设计，支持PC和移动端

### 🔐 安全特性
- BCrypt密码加密
- JWT Token认证
- 会话过期管理
- 跨站请求防护
- 输入验证和过滤

## 📦 系统架构

### 后端技术栈
- **Spring Boot** - 应用框架
- **MyBatis** - 数据库ORM
- **MySQL** - 数据存储
- **Spring Security** - 密码加密
- **JWT** - 令牌认证

### 前端技术栈
- **HTML5/CSS3/JavaScript** - 基础技术
- **Font Awesome** - 图标库
- **Responsive Design** - 响应式设计

### 数据库设计
- **users** - 用户基础信息表
- **user_sessions** - 用户会话管理表
- **现有表扩展** - 添加user_id关联字段

## 🛠️ 部署步骤

### 第一步：数据库初始化

1. **备份现有数据**（重要！）
```bash
mysqldump -u your_username -p english > backup_$(date +%Y%m%d_%H%M%S).sql
```

2. **执行初始化脚本**
```bash
mysql -u your_username -p english < database_init.sql
```

3. **验证数据库结构**
```sql
-- 检查新表是否创建成功
SHOW TABLES LIKE 'users';
SHOW TABLES LIKE 'user_sessions';

-- 检查现有表是否正确添加了user_id字段
DESCRIBE againenglishword;
DESCRIBE daily_record;
DESCRIBE study_record;
DESCRIBE word_record;
```

### 第二步：后端代码部署

1. **添加依赖**
确保你的`pom.xml`包含以下依赖：
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

2. **部署后端代码**
将以下文件添加到项目中：
- `com.austain.domain.po.User.java`
- `com.austain.domain.po.UserSession.java`
- `com.austain.domain.dto.LoginRequest.java`
- `com.austain.domain.dto.RegisterRequest.java`
- `com.austain.domain.dto.UserInfo.java`
- `com.austain.domain.dto.ChangePasswordRequest.java`
- `com.austain.mapper.UserMapper.java`
- `com.austain.service.UserService.java`
- `com.austain.service.impl.UserServiceImpl.java`
- `com.austain.controller.UserController.java`
- `com.austain.interceptor.AuthInterceptor.java`
- 更新的`com.austain.config.WebConfig.java`

3. **重启Spring Boot应用**
```bash
# 如果使用jar包部署
java -jar English_back_system.jar

# 如果使用IDE开发
# 重启应用程序
```

### 第三步：前端代码部署

1. **部署前端页面**
将以下文件复制到nginx的html目录：
- `login.html` - 登录页面
- `register.html` - 注册页面  
- `profile.html` - 个人中心页面
- `assets/js/user-info.js` - 用户信息组件

2. **更新现有页面**
在以下页面中添加用户信息组件：
- `index.html`
- `function.html`
- `AllBook.html`
- `wrongBook.html`

3. **更新nginx配置**
```bash
# 重新加载nginx配置
nginx -s reload
```

## 🎯 默认账户信息

### 管理员账户
- **用户名**: `admin`
- **密码**: `admin123`
- **角色**: 管理员
- **权限**: 可查看所有用户数据

### 测试用户账户
- **用户名**: `testuser`
- **密码**: `test123`
- **角色**: 普通用户
- **权限**: 只能查看自己的数据

> ⚠️ **安全提醒**: 部署完成后请立即修改默认密码！

## 🔧 配置说明

### 后端配置

1. **数据库连接**
确保`application.properties`中的数据库连接正确：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/english
spring.datasource.username=your_username
spring.datasource.password=your_password
```

2. **跨域配置**
如果前后端部署在不同域名，需要调整CORS配置：
```java
// WebConfig.java
.allowedOrigins("http://your-frontend-domain.com")
```

### 前端配置

1. **API地址配置**
如果后端地址不是`192.168.43.106:8080`，需要修改前端页面中的API地址：
```javascript
// 在各个前端页面中查找并替换
const API_BASE_URL = 'http://your-backend-url:8080';
```

## 📖 使用指南

### 用户注册流程
1. 访问 `/register` 页面
2. 填写用户名、邮箱（可选）、昵称、密码
3. 系统会实时验证用户名和邮箱可用性
4. 注册成功后自动跳转到登录页面

### 用户登录流程  
1. 访问 `/login` 页面
2. 输入用户名和密码
3. 登录成功后跳转到功能页面
4. 系统会在右上角显示用户信息组件

### 个人中心功能
1. 点击右上角用户头像进入个人中心
2. **个人信息**：修改昵称、邮箱、上传头像
3. **安全设置**：修改密码、退出登录
4. **学习统计**：查看学习数据和错词统计

### 错词本功能
1. 访问 `/wrongbook` 页面查看个人错词
2. 支持按课本、错误次数筛选
3. 可以删除已掌握的错词
4. 管理员可查看所有用户错词

## 🔍 功能测试

### 测试用户注册
```bash
curl -X POST http://localhost:8080/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "password123",
    "confirmPassword": "password123",
    "email": "newuser@example.com",
    "nickname": "新用户"
  }'
```

### 测试用户登录
```bash
curl -X POST http://localhost:8080/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 测试受保护接口
```bash
# 使用登录返回的token
curl -X GET http://localhost:8080/english/wrongbook/all \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## 🚨 故障排除

### 常见问题

1. **登录后跳转到空白页面**
   - 检查nginx配置是否正确
   - 确认前端文件是否部署到位

2. **API调用返回401错误**
   - 检查Token是否正确传递
   - 确认用户是否已登录
   - 验证后端拦截器配置

3. **数据库连接错误**
   - 检查数据库服务是否运行
   - 验证连接参数是否正确
   - 确认数据库用户权限

4. **前端显示网络错误**
   - 检查后端服务是否启动
   - 确认API地址配置是否正确
   - 验证CORS配置

### 日志调试

1. **后端日志**
```bash
# 查看Spring Boot应用日志
tail -f logs/application.log
```

2. **前端调试**
```javascript
// 在浏览器控制台中查看网络请求
// F12 -> Network -> 查看API调用状态
```

## 🔒 安全建议

### 生产环境安全配置

1. **修改默认密码**
```sql
-- 登录系统后修改管理员密码
UPDATE users SET password = '$2a$10$新的加密密码' WHERE username = 'admin';
```

2. **配置HTTPS**
```nginx
server {
    listen 443 ssl;
    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;
}
```

3. **设置会话超时**
```java
// 在UserServiceImpl中调整token过期时间
session.setExpiresAt(LocalDateTime.now().plusHours(24)); // 24小时过期
```

4. **定期清理过期会话**
```sql
-- 设置定时任务清理过期会话
CALL CleanExpiredSessions();
```

## 📈 性能优化

### 数据库优化
1. **添加适当索引**
```sql
-- 为常用查询字段添加索引
CREATE INDEX idx_againenglishword_user_create ON againenglishword(user_id, create_time);
CREATE INDEX idx_user_sessions_expires ON user_sessions(expires_at);
```

2. **定期数据清理**
```sql
-- 清理过期会话
DELETE FROM user_sessions WHERE expires_at < NOW();
```

### 前端优化
1. **启用gzip压缩**
```nginx
gzip on;
gzip_types text/css application/javascript application/json;
```

2. **设置缓存头**
```nginx
location ~* \.(css|js|png|jpg|jpeg|gif|ico)$ {
    expires 1y;
    add_header Cache-Control "public, immutable";
}
```

## 🎉 部署完成验证

部署完成后，请按以下步骤验证系统功能：

### 基础功能验证
- [ ] 访问 `http://localhost/login` 能正常显示登录页面
- [ ] 访问 `http://localhost/register` 能正常显示注册页面
- [ ] 使用默认管理员账户能正常登录
- [ ] 登录后能看到右上角用户信息组件
- [ ] 点击个人中心能正常访问和修改信息

### 权限功能验证
- [ ] 普通用户只能查看自己的错词数据
- [ ] 管理员可以查看所有用户数据
- [ ] 未登录用户访问受保护页面会跳转到登录页面

### 错词本功能验证
- [ ] 错词本页面需要登录才能访问
- [ ] 可以正常添加、删除错词
- [ ] 筛选功能正常工作
- [ ] 统计数据正确显示

---

🎊 **恭喜！用户认证系统部署完成！**

如有问题，请检查上述故障排除部分或联系系统管理员。
