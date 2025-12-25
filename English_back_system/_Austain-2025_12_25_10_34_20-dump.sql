-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: english_web
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `againenglishword`
--

DROP TABLE IF EXISTS `againenglishword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `againenglishword` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  `bookname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'nowhere' COMMENT '来源',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_againenglishword_user_id` (`user_id`),
  CONSTRAINT `fk_againenglishword_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=408 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='生单词表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `daily_record`
--

DROP TABLE IF EXISTS `daily_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '日常学习编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学习记录',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `selected` int DEFAULT '0' COMMENT '选中今天的复习内容',
  `already_reviewed` int DEFAULT '0' COMMENT '今日已经复习',
  PRIMARY KEY (`id`),
  KEY `idx_daily_record_user_id` (`user_id`),
  CONSTRAINT `fk_daily_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='日常学习记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `englishword200`
--

DROP TABLE IF EXISTS `englishword200`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `englishword200` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='单词表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `englishword2820`
--

DROP TABLE IF EXISTS `englishword2820`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `englishword2820` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2821 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='单词表常用2820';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `englishword4420`
--

DROP TABLE IF EXISTS `englishword4420`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `englishword4420` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4421 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='单词表常用4420';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `englishword575`
--

DROP TABLE IF EXISTS `englishword575`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `englishword575` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=576 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='单词表常用576';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `englishword692`
--

DROP TABLE IF EXISTS `englishword692`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `englishword692` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=693 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='单词表常用692';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `error_word_list`
--

DROP TABLE IF EXISTS `error_word_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `error_word_list` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '1' COMMENT '错误次数',
  `bookname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'nowhere' COMMENT '来源',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_error_word_list_user_id` (`user_id`),
  CONSTRAINT `fk_error_word_list_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='所有用户的错词表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = gbk */ ;
/*!50003 SET character_set_results = gbk */ ;
/*!50003 SET collation_connection  = gbk_chinese_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS after_error_word_delete */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_error_word_delete` AFTER DELETE ON `error_word_list` FOR EACH ROW BEGIN
    
    INSERT INTO `Error_Word_Review_List` (
        `user_id`,
        `word`,
        `chinese`,
        `pronounce`,
        `times`,
        `bookname`
    ) VALUES (
                 OLD.`user_id`,
                 OLD.`word`,
                 OLD.`chinese`,
                 OLD.`pronounce`,
                 OLD.`times`,
                 OLD.`bookname`
             );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `error_word_review_list`
--

DROP TABLE IF EXISTS `error_word_review_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `error_word_review_list` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '1' COMMENT '错误次数',
  `bookname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'nowhere' COMMENT '来源',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_error_word_review_list_user_id` (`user_id`),
  CONSTRAINT `fk_error_word_review_list_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='所有用户的错词二次复习表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `finally_again_word`
--

DROP TABLE IF EXISTS `finally_again_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `finally_again_word` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词',
  `chinese` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文释义',
  `pronounce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '音标',
  `times` int NOT NULL DEFAULT '0' COMMENT '出现次数',
  `bookname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'nowhere' COMMENT '来源',
  `user_id` bigint NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_finally_again_word_user_id` (`user_id`),
  CONSTRAINT `fk_finally_again_word_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=601 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='30天之后不会的单词';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jottings`
--

DROP TABLE IF EXISTS `jottings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jottings` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '日常学习编号',
  `english` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '英文',
  `chinese` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `reviewed` tinyint(1) DEFAULT '0' COMMENT '已经复习',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `fk_jottings_user_id` (`user_id`),
  CONSTRAINT `fk_jottings_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='随身记录积累表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `listening_record`
--

DROP TABLE IF EXISTS `listening_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listening_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '听力学习编号',
  `record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学习记录',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `selected` int DEFAULT '0' COMMENT '选中今天的复习内容',
  `already_reviewed` int DEFAULT '0' COMMENT '今日已经复习',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `fk_listening_record_user_id` (`user_id`),
  CONSTRAINT `fk_listening_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='听力学习记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sentence200`
--

DROP TABLE IF EXISTS `sentence200`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sentence200` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '句子编号',
  `sentence` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '英文句子',
  `chinese` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文翻译',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `fk_sentence200_user_id` (`user_id`),
  CONSTRAINT `fk_sentence200_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='写作200句';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sentence_record`
--

DROP TABLE IF EXISTS `sentence_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sentence_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '句子编号',
  `record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学习记录',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `selected` int DEFAULT '0' COMMENT '选中今天的复习内容',
  `already_reviewed` int DEFAULT '0' COMMENT '今日已经复习',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  KEY `fk_sentence_record_user_id` (`user_id`),
  CONSTRAINT `fk_sentence_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='句子学习记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `study_record`
--

DROP TABLE IF EXISTS `study_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '单词编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学习记录',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `selected` int DEFAULT '0' COMMENT '选中今天的复习内容',
  `already_reviewed` int DEFAULT '0' COMMENT '今日已经复习',
  PRIMARY KEY (`id`),
  KEY `idx_study_record_user_id` (`user_id`),
  CONSTRAINT `fk_study_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学习记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `study_sessions`
--

DROP TABLE IF EXISTS `study_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学习会话ID',
  `user_id` bigint NOT NULL COMMENT '所属用户',
  `book_id` bigint DEFAULT NULL COMMENT '所属课本ID，引用 user_books.id',
  `scene` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学习场景（单词/听力/写作等）',
  `source` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '入口来源（web/mobile等）',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `duration_seconds` int NOT NULL DEFAULT '0' COMMENT '学习时长（秒）',
  `status` enum('RUNNING','COMPLETED','TIMEOUT','CANCELLED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'RUNNING' COMMENT '会话状态',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_study_sessions_user_id` (`user_id`),
  KEY `idx_study_sessions_book_id` (`book_id`),
  KEY `idx_study_sessions_start_time` (`start_time`),
  CONSTRAINT `fk_study_sessions_book_id` FOREIGN KEY (`book_id`) REFERENCES `user_books` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_study_sessions_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学习会话记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_books`
--

DROP TABLE IF EXISTS `user_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_books` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '所属用户',
  `book_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '课本名称',
  `book_code` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课本标识/英文代号',
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `cover_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面地址',
  `word_count` int DEFAULT NULL COMMENT '预计词汇量',
  `visibility` enum('PRIVATE','PUBLIC') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'PRIVATE' COMMENT '可见范围',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 1-启用 0-禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_book_name` (`user_id`,`book_name`),
  UNIQUE KEY `uk_book_code` (`book_code`),
  KEY `idx_user_books_user_id` (`user_id`),
  CONSTRAINT `fk_user_books_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户自定义课本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `user_learning_time_stats`
--

DROP TABLE IF EXISTS `user_learning_time_stats`;
/*!50001 DROP VIEW IF EXISTS `user_learning_time_stats`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_learning_time_stats` AS SELECT 
 1 AS `user_id`,
 1 AS `book_id`,
 1 AS `book_name`,
 1 AS `total_seconds`,
 1 AS `today_seconds`,
 1 AS `last7days_seconds`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user_sessions`
--

DROP TABLE IF EXISTS `user_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `session_token` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '会话令牌',
  `expires_at` datetime NOT NULL COMMENT '过期时间',
  `ip_address` varchar(45) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户代理',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_session_token` (`session_token`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_expires_at` (`expires_at`),
  CONSTRAINT `fk_sessions_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户会话表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `user_statistics`
--

DROP TABLE IF EXISTS `user_statistics`;
/*!50001 DROP VIEW IF EXISTS `user_statistics`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_statistics` AS SELECT 
 1 AS `user_id`,
 1 AS `username`,
 1 AS `nickname`,
 1 AS `role`,
 1 AS `user_create_time`,
 1 AS `last_login_time`,
 1 AS `wrong_words_count`,
 1 AS `study_records_count`,
 1 AS `daily_records_count`,
 1 AS `word_records_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `user_word_mastery_stats`
--

DROP TABLE IF EXISTS `user_word_mastery_stats`;
/*!50001 DROP VIEW IF EXISTS `user_word_mastery_stats`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_word_mastery_stats` AS SELECT 
 1 AS `user_id`,
 1 AS `book_id`,
 1 AS `book_name`,
 1 AS `mastered_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(300) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码(加密)',
  `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) COLLATE utf8mb4_general_ci DEFAULT 'https://austain-java-ai-web.oss-cn-beijing.aliyuncs.com/chenhaoxing.jpg' COMMENT '头像URL',
  `role` enum('USER','ADMIN') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'USER' COMMENT '用户角色',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word_mastery`
--

DROP TABLE IF EXISTS `word_mastery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word_mastery` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '所属用户',
  `book_id` bigint NOT NULL COMMENT '所属课本ID（user_books.id）',
  `word_bank` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '词库来源（如 englishword4420）',
  `word_id` bigint DEFAULT NULL COMMENT '词库中的单词ID',
  `word_text` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单词原文快照',
  `proficiency_score` tinyint DEFAULT NULL COMMENT '熟练度(0-100)',
  `mastered` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已掌握',
  `first_mastered_time` datetime DEFAULT NULL COMMENT '首次判定掌握时间',
  `last_mastered_time` datetime DEFAULT NULL COMMENT '最近一次判定掌握时间',
  `review_count` int NOT NULL DEFAULT '0' COMMENT '复习次数',
  `regression_count` int NOT NULL DEFAULT '0' COMMENT '遗忘次数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_word_mastery_user_book_word` (`user_id`,`book_id`,`word_id`,`word_text`),
  KEY `idx_word_mastery_user` (`user_id`),
  KEY `idx_word_mastery_book` (`book_id`),
  KEY `idx_word_mastery_mastered` (`mastered`),
  CONSTRAINT `fk_word_mastery_book_id` FOREIGN KEY (`book_id`) REFERENCES `user_books` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_word_mastery_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户词汇掌握记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `word_record`
--

DROP TABLE IF EXISTS `word_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '日常学习编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学习记录',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `selected` int DEFAULT '0' COMMENT '选中今天的复习内容',
  `already_reviewed` int DEFAULT '0' COMMENT '今日已经复习',
  PRIMARY KEY (`id`),
  KEY `idx_word_record_user_id` (`user_id`),
  CONSTRAINT `fk_word_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='日常学习记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'english_web'
--
/*!50003 DROP PROCEDURE IF EXISTS `CleanExpiredSessions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`Austain`@`127.0.0.1` PROCEDURE `CleanExpiredSessions`()
BEGIN
    DELETE FROM user_sessions WHERE expires_at < NOW();
    SELECT ROW_COUNT() as cleaned_sessions_count;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `user_learning_time_stats`
--

/*!50001 DROP VIEW IF EXISTS `user_learning_time_stats`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`Austain`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `user_learning_time_stats` AS select `s`.`user_id` AS `user_id`,coalesce(`s`.`book_id`,0) AS `book_id`,coalesce(`b`.`book_name`,'ALL') AS `book_name`,sum((case when (`s`.`status` in ('COMPLETED','TIMEOUT')) then `s`.`duration_seconds` else 0 end)) AS `total_seconds`,sum((case when ((`s`.`status` in ('COMPLETED','TIMEOUT')) and (cast(`s`.`start_time` as date) = curdate())) then `s`.`duration_seconds` else 0 end)) AS `today_seconds`,sum((case when ((`s`.`status` in ('COMPLETED','TIMEOUT')) and (`s`.`start_time` >= (curdate() - interval 6 day))) then `s`.`duration_seconds` else 0 end)) AS `last7days_seconds` from (`study_sessions` `s` left join `user_books` `b` on((`s`.`book_id` = `b`.`id`))) group by `s`.`user_id`,coalesce(`s`.`book_id`,0),coalesce(`b`.`book_name`,'ALL') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_statistics`
--

/*!50001 DROP VIEW IF EXISTS `user_statistics`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = gbk */;
/*!50001 SET character_set_results     = gbk */;
/*!50001 SET collation_connection      = gbk_chinese_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY INVOKER */
/*!50001 VIEW `user_statistics` AS select `u`.`id` AS `user_id`,`u`.`username` AS `username`,`u`.`nickname` AS `nickname`,`u`.`role` AS `role`,`u`.`create_time` AS `user_create_time`,`u`.`last_login_time` AS `last_login_time`,coalesce(`wrong_words`.`count`,0) AS `wrong_words_count`,coalesce(`study_records`.`count`,0) AS `study_records_count`,coalesce(`daily_records`.`count`,0) AS `daily_records_count`,coalesce(`word_records`.`count`,0) AS `word_records_count` from ((((`users` `u` left join (select `againenglishword`.`user_id` AS `user_id`,count(0) AS `count` from `againenglishword` group by `againenglishword`.`user_id`) `wrong_words` on((`u`.`id` = `wrong_words`.`user_id`))) left join (select `study_record`.`user_id` AS `user_id`,count(0) AS `count` from `study_record` group by `study_record`.`user_id`) `study_records` on((`u`.`id` = `study_records`.`user_id`))) left join (select `daily_record`.`user_id` AS `user_id`,count(0) AS `count` from `daily_record` group by `daily_record`.`user_id`) `daily_records` on((`u`.`id` = `daily_records`.`user_id`))) left join (select `word_record`.`user_id` AS `user_id`,count(0) AS `count` from `word_record` group by `word_record`.`user_id`) `word_records` on((`u`.`id` = `word_records`.`user_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_word_mastery_stats`
--

/*!50001 DROP VIEW IF EXISTS `user_word_mastery_stats`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`Austain`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `user_word_mastery_stats` AS select `wm`.`user_id` AS `user_id`,`wm`.`book_id` AS `book_id`,`b`.`book_name` AS `book_name`,count((case when (`wm`.`mastered` = 1) then 1 end)) AS `mastered_count` from (`word_mastery` `wm` left join `user_books` `b` on((`wm`.`book_id` = `b`.`id`))) group by `wm`.`user_id`,`wm`.`book_id`,`b`.`book_name` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-25 10:34:21
