/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:49:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `collectId` varchar(100) NOT NULL COMMENT '收藏ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `goodsId` varchar(100) DEFAULT NULL COMMENT '商品ID',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`collectId`,`uid`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_collectId` (`collectId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
