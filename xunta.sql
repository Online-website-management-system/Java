/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : localhost:3306
 Source Schema         : xunta

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 26/02/2023 21:37:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `collect_id` bigint(20) NOT NULL COMMENT '收藏ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `remark` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 1, NULL, NULL, NULL, '2021-02-18 18:26:10', '2021-02-18 18:26:10');
INSERT INTO `account` VALUES (2, 2, NULL, NULL, NULL, '2021-02-18 18:31:19', '2021-02-18 18:31:19');
INSERT INTO `account` VALUES (3, 3, NULL, NULL, NULL, '2021-02-18 20:08:44', '2021-02-18 20:08:44');
INSERT INTO `account` VALUES (4, 4, NULL, NULL, NULL, '2021-02-18 20:10:46', '2021-02-18 20:10:46');
INSERT INTO `account` VALUES (5, 5, NULL, NULL, NULL, '2021-02-18 20:13:13', '2021-02-18 20:13:13');
INSERT INTO `account` VALUES (6, 6, '123456', '123456', NULL, '2021-09-20 16:02:39', '2021-09-20 16:05:39');
INSERT INTO `account` VALUES (8, 8, '123456', '123456', NULL, '2021-09-20 19:45:40', '2021-09-20 19:46:40');
INSERT INTO `account` VALUES (9, 15, '18888888888', '1008611', NULL, '2022-02-11 09:59:02', '2022-02-25 17:21:10');
INSERT INTO `account` VALUES (10, 16, NULL, NULL, NULL, '2022-02-11 10:00:28', '2022-02-11 10:00:28');
INSERT INTO `account` VALUES (11, 17, '6666', '77777', NULL, '2022-04-22 20:22:46', '2022-04-22 20:28:28');
INSERT INTO `account` VALUES (12, 18, '18888888888', '18888888888', NULL, '2022-04-22 20:40:56', '2023-02-26 21:34:30');
INSERT INTO `account` VALUES (14, 20, '2313', '123213', NULL, '2022-04-29 13:04:51', '2022-04-30 08:36:07');
INSERT INTO `account` VALUES (15, 21, NULL, NULL, NULL, '2022-04-29 21:19:12', '2022-04-29 21:19:12');
INSERT INTO `account` VALUES (16, 22, '18888888888', '18888888888', NULL, '2022-04-30 11:21:34', '2023-02-26 21:34:30');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_id` bigint(11) NOT NULL COMMENT '类型ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
  `rank` enum('parent','son') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'parent' COMMENT '类别 parent-父类型  son-子类型',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 0, '常用网址', 'parent', 1, '2021-02-18 18:23:53', '2021-02-18 20:13:51');
INSERT INTO `category` VALUES (2, 2, '学习网站', 'parent', 2, '2021-09-20 15:59:24', '2022-04-29 13:07:46');
INSERT INTO `category` VALUES (4, 3, '测试分类', 'parent', 4, '2021-09-20 19:43:18', '2021-09-20 19:43:18');
INSERT INTO `category` VALUES (5, 2, 'IT学习网站', 'son', 7, '2022-02-11 09:42:35', '2022-04-29 13:07:50');
INSERT INTO `category` VALUES (6, 2, '数学学习网站', 'son', 5, '2022-02-11 09:43:59', '2022-04-29 13:07:51');
INSERT INTO `category` VALUES (7, 2, '英语学习网站', 'son', 6, '2022-02-11 09:44:15', '2022-04-29 13:07:51');
INSERT INTO `category` VALUES (8, 2, '电影网站', 'parent', 10, '2022-02-11 09:55:02', '2022-04-29 13:08:10');
INSERT INTO `category` VALUES (9, 2, '游戏网站', 'parent', 9, '2022-02-11 09:55:11', '2022-04-29 13:08:09');
INSERT INTO `category` VALUES (10, 2, '网页设计', 'parent', 11, '2022-02-11 09:56:50', '2022-04-29 13:08:10');
INSERT INTO `category` VALUES (11, 2, '音乐网站', 'parent', 8, '2022-02-11 09:57:25', '2022-04-29 13:08:09');
INSERT INTO `category` VALUES (12, 5, 'Java语言', 'son', 15, '2022-02-26 16:33:05', '2022-04-29 13:07:54');
INSERT INTO `category` VALUES (13, 12, 'Spring Boot框架', 'son', 13, '2022-02-26 16:33:28', '2022-02-26 16:33:34');
INSERT INTO `category` VALUES (15, 5, 'C语言', 'son', 12, '2022-02-26 16:34:10', '2022-04-29 13:07:54');
INSERT INTO `category` VALUES (16, 13, 'mvc', 'son', 16, '2022-04-22 20:27:42', '2022-04-22 20:27:42');
INSERT INTO `category` VALUES (17, 16, 'JSP', 'son', 17, '2022-04-30 08:38:19', '2022-04-30 08:38:19');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `user_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL COMMENT '类型ID',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收藏地址',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收藏标题',
  `logo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '收藏logo',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收藏介绍',
  `home` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否首页 0-否 1-是',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `praise` int(11) NOT NULL DEFAULT 0 COMMENT '收藏点赞数',
  `visit` int(11) NOT NULL DEFAULT 0 COMMENT '收藏访问数',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (4, 0, 1, 'http://192.168.238.246:8080/', '寻它网址导航系统', 'http://www.junengw.com/logo.png', '在线的网址收藏书签平台,网址在线保存', 1, 4, 0, 1, '2021-02-18 20:10:46', '2022-02-07 16:43:09');
INSERT INTO `collect` VALUES (6, 2, 0, 'http://127.0.0.1:8080/index/collect/edit', '测试新增网址', 'http://www.junengw.com/logo.png', '新鲜出炉最新添加的网址看一看瞧一瞧啦', 1, 6, 0, 0, '2021-09-20 16:02:39', '2022-02-11 09:57:52');
INSERT INTO `collect` VALUES (8, 3, 4, 'http://127.0.0.1:8080/index/collect/edit', '测试增加', 'http://www.junengw.com/logo.png', '测试增加网址', 1, 8, 0, 2, '2021-09-20 19:45:40', '2022-04-22 20:55:34');
INSERT INTO `collect` VALUES (9, 0, 1, 'http://jwmis.hhtc.edu.cn/', '怀化学院新教务系统', 'http://jwmis.hhtc.edu.cn/assets/images/swiper.png', '教务一体化系统', 1, 1, 0, 74, '2021-02-18 18:26:10', '2022-02-09 08:44:12');
INSERT INTO `collect` VALUES (10, 0, 1, 'http://xgxt.hhtc.edu.cn/', '怀化学院学工系统', 'http://xgxt.hhtc.edu.cn/service/uploadserver/WebFiles/images/userhall/SelfBackgroundImg/202012/86d9ba09-72ec-4976-905a-339f7b7969a5.jpg', '学工一体化平台', 1, 2, 0, 9, '2021-02-18 18:31:19', '2022-02-09 08:44:07');
INSERT INTO `collect` VALUES (11, 0, 1, 'http://stu.ityxb.com/', '传智 | 高校学习平台-首页', 'http://stu.ityxb.com/favicon.ico', '学习平台，在线学习，自学平台，免费IT学习，java，前端，学习路线，学习笔记，毕业设计，公开课，大学生交流，大学生交友，大学生社群。', 1, 3, 0, 8, '2021-02-18 20:08:44', '2021-07-09 13:22:29');
INSERT INTO `collect` VALUES (12, 0, 1, 'http://i.chaoxing.com/', '学习通', 'http://p.ananas.chaoxing.com/star3/144_144/55f7addde4b040cfea17af65.png?rw=512&rh=512', '学习通是基于微服务架构打造的课程学习，知识传播与管理分享平台。它利用超星20余年来积累的海量的图书、期刊、报纸、视频、原创等资源，集知识管理、课程学习、专题创作，办公应用为一体，为读者提供一站式学习与工作环境。', 1, 4, 0, 19, '2021-02-18 20:10:46', '2022-01-07 21:26:45');
INSERT INTO `collect` VALUES (13, 0, 1, 'https://zhtj.youth.cn/zhtj/', '网上共青团·智慧团建', 'https://zhtj.youth.cn/zhtj/static/img/web_logo.png', '网上共青团·智慧团建', 1, 5, 0, 1, '2021-02-18 20:13:13', '2021-08-27 16:43:37');
INSERT INTO `collect` VALUES (14, 2, 11, 'https://music.163.com/', '我的网易云音乐', 'http://www.junengw.com/logo.png', '网易云音乐是一款专注于发现与分享的音乐产品，依托专业音乐人、DJ、好友推荐及社交功能，为用户打造全新的音乐生活。', 1, 6, 0, 2, '2021-09-20 16:02:39', '2022-04-29 13:06:03');
INSERT INTO `collect` VALUES (15, 2, 2, 'https://www.xuxueli.com/xxl-job/', '分布式任务调度平台XXL-JOB', 'https://www.xuxueli.com/favicon.ico', 'XXL-JOB是一个轻量级分布式任务调度平台，其核心设计目标是开发迅速、学习简单、轻量级、易扩展。现已开放源代码并接入多家公司线上产品线，开箱即用。', 1, 16, 0, 1, '2022-02-11 09:59:02', '2022-04-29 21:20:10');
INSERT INTO `collect` VALUES (16, 2, 5, 'https://edu.51cto.com/', '51CTO学堂-IT培训_IT人充电上51CTO学堂', 'https://edu.51cto.com/favicon.ico', '51CTO学堂为IT技术人员终生学习提供最丰富的课程资源库,数千名专业讲师和大厂工程师倾力分享了数万门在线视频课程,几乎覆盖了IT技术的各个领域:java、python、php、c、前端、数据库、区块链、运维等，帮助每个渴望成长的IT技术工程师技能提升，学有所成！', 1, 15, 0, 0, '2022-02-11 10:00:28', '2022-04-22 20:57:11');
INSERT INTO `collect` VALUES (17, 2, 2, 'https://www.runoob.com/nodejs/nodejs-install-setup.html', 'Node.js 安装配置 | 菜鸟教程', 'https://www.runoob.com/favicon.ico', 'Node.js 安装配置 本章节我们将向大家介绍在 Windows 和 Linux 上安装 Node.js 的方法。  本安装教程以 Node.js v4.4.3 LTS(长期支持版本)版本为例。 Node.js 安装包及源码下载地址为：https://nodejs.org/en/download/。   你可以根据不同平台系统选择你需要的 Node.js 安装包。 Node.js 历史版本下载地址：https://nodejs.org..', 0, 17, 0, 0, '2022-04-22 20:22:46', '2022-04-22 20:23:16');
INSERT INTO `collect` VALUES (18, 2, 0, 'https://i.chaoxing.com/', 'Sign in', 'https://i.chaoxing.com/favicon.ico', NULL, 1, 18, 0, 2, '2022-04-22 20:40:56', '2022-04-22 20:44:15');
INSERT INTO `collect` VALUES (20, 2, 0, 'https://www.baidu.com/?tn=78000241_49_hao_pg', '百度一下，你就知道', 'https://www.baidu.com/favicon.ico', '全球领先的中文搜索引擎、致力于让网民更便捷地获取信息，找到所求。百度超过千亿的中文网页数据库，可以瞬间找到相关的搜索结果。', 1, 20, 0, 2, '2022-04-29 13:04:51', '2022-04-29 13:05:47');
INSERT INTO `collect` VALUES (21, 2, 0, 'https://food.jd.com/?cu=true&utm_source=kong&utm_medium=tuiguang&utm_campaign=t_1003480280_&utm_term=c4e1cc7a6c464f989be4e2f51d0e7d71', '京东食品饮料-零食_酒水饮料_进口食品_休闲食品-京东', 'https://food.jd.com/favicon.ico', '京东JD.COM食品饮料频道,专业提供零食,酒水饮料,进口食品,休闲食品,地方特产,冲调饮料,粮油调味,生鲜等食品饮料的最新报价、促销、评论、导购、图片等相关信息!为您提供愉悦的网上购物体验! ', 1, 21, 0, 1, '2022-04-29 21:19:12', '2022-04-29 21:19:21');
INSERT INTO `collect` VALUES (22, 2, 0, 'https://passport2.chaoxing.com/login?fid=&newversion=true&refer=https%3A%2F%2Fi.chaoxing.com', '用户登录', 'https://passport2.chaoxing.com/favicon.ico', NULL, 1, 22, 0, 2, '2022-04-30 11:21:34', '2022-04-30 11:27:12');

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '网站设置ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站名称',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录页logo',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES (1, '寻它网址管理系统', 'http://file.junengw.com/junengw-xunta/20220429/20220429182806536881.jpg', '2021-02-14 12:14:18', '2022-04-29 18:30:11');

-- ----------------------------
-- Table structure for slideshow
-- ----------------------------
DROP TABLE IF EXISTS `slideshow`;
CREATE TABLE `slideshow`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '轮播图',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '轮播图链接',
  `img` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '轮播图图片',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '轮播图标题',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of slideshow
-- ----------------------------
INSERT INTO `slideshow` VALUES (1, '/', 'http://file.junengw.com/junengw-xunta/20210214/1.jpg', '寻它网址导航', '2020-11-19 22:26:34', '2022-02-25 17:40:59');
INSERT INTO `slideshow` VALUES (2, '/', 'http://file.junengw.com/junengw-xunta/20210214/2.jpg', '寻它网址导航', '2020-11-19 22:26:34', '2022-02-25 17:41:02');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '用户表主键\r\n',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机登录',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱登录',
  `qq_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ登录',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '个人主页' COMMENT '个人主页title',
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户域',
  `domain_role` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户查看域是否需要权限 0-需要 1-不需要',
  `roles` enum('ROLE_MEMBER','ROLE_ADMIN') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ROLE_MEMBER' COMMENT '用户身份 member-普通用户 admin-管理员',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '875066875', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, '小孟在线分享', '/L9AchT', 0, 'ROLE_MEMBER', '2021-02-18 18:23:38', '2022-02-07 15:43:00');
INSERT INTO `user` VALUES (2, '123456', 'e10adc3949ba59abbe56e057f20f883e', '18888888888', NULL, 'DB5FB220DC85B1A17450543D353D8392', '个人主页', '/888', 1, 'ROLE_MEMBER', '2021-09-20 15:36:11', '2023-02-26 21:34:18');
INSERT INTO `user` VALUES (3, '1234567', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, '个人主页', '/0KD8O7', 1, 'ROLE_MEMBER', '2021-09-20 19:40:13', '2022-04-22 20:38:52');
INSERT INTO `user` VALUES (4, '12345678', '25d55ad283aa400af464c76d713c07ad', NULL, NULL, NULL, '个人主页', '/KLONFK', 0, 'ROLE_MEMBER', '2022-02-10 14:32:04', '2022-02-10 14:32:04');

SET FOREIGN_KEY_CHECKS = 1;
