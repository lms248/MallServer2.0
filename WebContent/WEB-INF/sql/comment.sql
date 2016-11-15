/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:49:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `commentId` varchar(255) NOT NULL COMMENT '评论ID',
  `goodsId` varchar(255) DEFAULT NULL COMMENT '商品ID',
  `orderId` varchar(255) DEFAULT NULL COMMENT '订单ID',
  `uid` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `content` text COMMENT '评论内容',
  `star` int(11) DEFAULT NULL COMMENT '评论星级',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`commentId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_commentId` (`commentId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
