/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-08 18:28:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `ordeId` bigint(20) NOT NULL COMMENT '订单ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `goodsId` bigint(20) NOT NULL COMMENT '商品ID',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `tags` varchar(255) DEFAULT NULL COMMENT '属性标签',
  `price` double DEFAULT NULL COMMENT '总价格',
  `createTime` bigint(20) DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`id`,`ordeId`,`uid`,`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
