/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:50:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `communityId` varchar(100) DEFAULT NULL COMMENT '社区ID',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `details` text COMMENT '描述',
  `imageList` varchar(1000) DEFAULT NULL COMMENT '图片列表',
  `thumbList` varchar(1000) DEFAULT NULL COMMENT '缩略图列表',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_communityId` (`communityId`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
