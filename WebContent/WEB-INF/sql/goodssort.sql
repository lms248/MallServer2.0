/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-12 20:08:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goodssort
-- ----------------------------
DROP TABLE IF EXISTS `goodssort`;
CREATE TABLE `goodssort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodssortId` bigint(20) DEFAULT NULL COMMENT '商品分类ID',
  `level_1` int(11) DEFAULT NULL COMMENT '类别一id',
  `level_2` int(11) DEFAULT NULL COMMENT '类别二id',
  `level_3` int(11) DEFAULT NULL COMMENT '类别三id',
  `goodsId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
