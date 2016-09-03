/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-03 16:28:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `cardId` bigint(20) NOT NULL COMMENT '购物车ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `goodsList` mediumtext COMMENT '商品列表信息',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`cardId`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
