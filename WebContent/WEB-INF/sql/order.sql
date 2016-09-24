/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-24 18:46:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `ordeId` bigint(20) NOT NULL COMMENT '订单ID',
  `payId` bigint(20) DEFAULT NULL COMMENT '订单支付ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `shopId` bigint(20) NOT NULL COMMENT '店铺ID',
  `goodsList` text COMMENT '数量',
  `addressId` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
  `status` int(11) DEFAULT NULL COMMENT '总价格',
  `createTime` bigint(20) DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`id`,`ordeId`,`uid`,`shopId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
