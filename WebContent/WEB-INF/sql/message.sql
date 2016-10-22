/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-10-22 14:19:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `messageId` varchar(100) DEFAULT NULL COMMENT '消息ID',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
