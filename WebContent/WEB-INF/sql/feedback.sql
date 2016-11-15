/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:50:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `feedbackId` varchar(100) NOT NULL COMMENT '反馈ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `info` varchar(1000) DEFAULT NULL COMMENT '反馈内容',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`feedbackId`,`uid`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_feedbackId` (`feedbackId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
