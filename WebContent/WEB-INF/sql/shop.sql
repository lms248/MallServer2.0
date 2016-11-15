/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:51:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `shopId` varchar(100) NOT NULL COMMENT '商店ID',
  `name` varchar(255) DEFAULT NULL COMMENT '商店名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `details` text COMMENT '描述，介绍',
  `logo` varchar(255) DEFAULT NULL COMMENT '店铺logo',
  `logoThumb` varchar(255) DEFAULT NULL COMMENT '店铺logo缩略图',
  `image` varchar(255) DEFAULT NULL COMMENT '背景图片',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '背景缩略图',
  `contactPhone` varchar(255) DEFAULT NULL COMMENT '联系客服电话',
  `type` varchar(255) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`shopId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_shopId` (`shopId`) USING BTREE,
  KEY `index_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
