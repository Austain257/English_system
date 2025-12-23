# 英语词汇学习系统 (English Learning System)

一个功能完整的英语词汇学习系统，支持多种学习模式、AI文章生成和学习记录管理。

## 📋 项目概述

本系统是一个基于Spring Boot + HTML前端的英语学习平台，提供了词汇学习、背诵练习、游戏化学习、学习记录管理等多种功能，帮助用户系统性地学习英语词汇。

## ✨ 主要功能

### 📚 词汇学习模块
- **词汇浏览**: 支持按范围浏览单词，显示音标、释义和出现频率
- **词汇背诵**: 移动端优化的单词背诵功能，支持收藏和复习
- **单词选项**: 选择题模式测试单词记忆效果
- **单词游戏**: 游戏化学习体验，提高学习趣味性
- **单词听写**: 英语单词默写练习功能

### 🤖 AI增强功能
- **智能文章生成**: 基于选定词汇范围生成连贯文章
- **流式输出**: 实时显示AI生成内容，提供打字机效果

### 📝 学习管理
- **学习记录**: 多表支持的学习记录系统（每日记录、学习记录、句子记录、单词记录、听力记录）
- **复习系统**: 基于艾宾浩斯遗忘曲线的复习提醒
- **知识积累**: 英语知识点收集和管理
- **学习统计**: 学习进度和效果统计

### 📖 多课本支持
- 支持多本英语教材的词汇学习
- 动态课本列表加载
- 课本词汇量和难度显示

## 🏗️ 技术架构

### 后端技术栈
- **框架**: Spring Boot 3.5.4
- **数据库**: MySQL + MyBatis
- **AI集成**: OpenAI API, 阿里云DashScope
- **其他**: 
  - PDF处理 (Apache PDFBox)
  - 分页查询 (PageHelper)
  - 定时任务支持

### 前端技术栈
- **基础**: HTML5 + CSS3 + JavaScript
- **样式**: 自定义CSS，统一紫蓝色渐变主题
- **交互**: 原生JavaScript，支持移动端适配
- **图标**: Font Awesome

### 数据库设计
- **词汇表**: 存储单词、音标、释义、频率等信息
- **学习记录表**: 多表存储不同类型的学习记录
- **知识积累表**: 存储用户收集的英语知识点
- **句子表**: 存储英语句子学习内容

## 🚀 部署说明

### 环境要求
- Java 17+
- MySQL 8.0+
- Maven 3.6+
- Nginx (可选，用于前端部署)

### 后端部署
1. 克隆项目到本地
```bash
git clone [项目地址]
cd English_system/English_back_system
```

2. 配置数据库
- 创建MySQL数据库
- 修改 `application.yml` 中的数据库连接配置

3. 启动后端服务
```bash
mvn clean install
mvn spring-boot:run
```
服务将在 `http://localhost:8080` 启动

### 前端部署
1. 配置Nginx或直接使用Web服务器
2. 将 `nginx-English/html/` 目录设为根目录
3. 确保前端页面中的API地址指向正确的后端服务

### API配置
前端页面中需要确保API地址配置正确：
- 本地开发: `http://192.168.43.106:8080` 或 `http://localhost:8080`
- 生产环境: 根据实际部署地址修改

## 📱 页面功能说明

| 页面文件 | 功能描述 |
|---------|---------|
| `index.html` | 主页，课本选择界面 |
| `function.html` | 功能选择页面 |
| `English-word.html` | 词汇学习页面 |
| `reciteWord.html` | 词汇背诵页面 |
| `wordOption.html` | 单词选择题测试 |
| `wordGame.html` | 单词游戏页面 |
| `worddictation.html` | 单词听写页面 |
| `sentence.html` | 句子学习页面 |
| `jottings.html` | 英语知识积累本 |
| `studyRecord-five.html` | 学习记录与清单 |
| `testGeneration.html` | AI文章生成页面 |
| `ArticleGeneration.html` | AI文章生成功能 |

## 🔧 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/english_system
    username: your_username
    password: your_password
```

### AI服务配置
```yaml
spring:
  ai:
    openai:
      api-key: your_openai_key
```

## 📊 API接口文档

### 词汇管理
- `GET /english/list` - 获取词汇列表
- `POST /english/add` - 添加词汇到学习集
- `POST /english/remove` - 从学习集移除词汇

### 学习记录
- `POST /study/record` - 添加学习记录
- `GET /study/list` - 获取学习记录
- `POST /study/mark` - 标记已复习

### 课本管理
- `GET /list` - 获取课本列表
- `GET /list/{bookName}` - 检查课本是否存在

### AI文章生成
- `GET /article/generation` - 生成文章（SSE流式输出）

## 🎯 使用指南

1. **选择课本**: 在主页选择要学习的英语课本
2. **选择功能**: 根据学习需求选择相应的学习模式
3. **词汇学习**: 设置词汇范围，开始学习单词
4. **记录管理**: 在学习记录页面查看和管理学习进度
5. **AI辅助**: 使用AI文章生成功能巩固词汇学习

## 🤝 贡献指南

欢迎提交Issue和Pull Request来改进项目！

## 📄 许可证

本项目采用MIT许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 👥 联系方式

- 项目维护者: Austain
- 邮箱: [您的邮箱]
- GitHub: [您的GitHub地址]

---

**注意**: 使用前请确保已正确配置数据库和AI服务的API密钥。
