/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:48:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `activityId` varchar(100) NOT NULL COMMENT '活动ID',
  `goodsId` varchar(100) DEFAULT NULL COMMENT '商品ID',
  `sortId` int(11) DEFAULT NULL COMMENT '分类ID',
  `title` varchar(500) DEFAULT NULL COMMENT '活动标题',
  `mark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`activityId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_activityId` (`activityId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
