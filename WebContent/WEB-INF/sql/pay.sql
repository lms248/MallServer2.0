/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-10-28 20:33:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pay
-- ----------------------------
DROP TABLE IF EXISTS `pay`;
CREATE TABLE `pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `payId` varchar(100) DEFAULT NULL COMMENT '支付ID',
  `payWay` varchar(255) DEFAULT NULL COMMENT '支付方式',
  `total_fee` int(11) DEFAULT NULL COMMENT '总金额（分）',
  `fee_type` varchar(255) DEFAULT NULL COMMENT ' 货币种类,默认人民币：CNY',
  `body` varchar(1000) DEFAULT NULL COMMENT '商品描述',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '支付订单号',
  `result_code` varchar(255) DEFAULT NULL COMMENT '业务结果,SUCCESS/FAIL',
  `err_code` varchar(255) DEFAULT NULL COMMENT '错误代码',
  `err_code_des` varchar(255) DEFAULT NULL COMMENT '错误代码描述',
  `status` int(11) DEFAULT NULL COMMENT '支付状态',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `payTime` bigint(20) DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
