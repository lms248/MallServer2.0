/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-22 17:57:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `collectId` bigint(20) NOT NULL COMMENT '收藏ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `goodsId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`collectId`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
