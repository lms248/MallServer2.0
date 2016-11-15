/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:50:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `orderId` varchar(100) NOT NULL COMMENT '订单ID',
  `payId` varchar(100) DEFAULT NULL COMMENT '订单支付ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `shopId` varchar(100) NOT NULL COMMENT '店铺ID',
  `goodsList` text COMMENT '数量',
  `addressId` varchar(100) DEFAULT NULL COMMENT '收货地址ID',
  `status` int(11) DEFAULT NULL COMMENT '总价格',
  `afterSaleService` text COMMENT '售后服务',
  `payWay` varchar(50) DEFAULT NULL COMMENT '支付方式',
  `createTime` bigint(20) DEFAULT NULL COMMENT '下单时间',
  `payTime` bigint(20) DEFAULT NULL COMMENT '支付时间',
  `deliverTime` bigint(20) DEFAULT NULL COMMENT '发货时间',
  `receiveTime` bigint(20) DEFAULT NULL COMMENT '收货时间',
  PRIMARY KEY (`id`,`orderId`,`uid`,`shopId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_orderId` (`orderId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
