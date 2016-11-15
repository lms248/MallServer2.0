/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:51:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `token` varchar(255) DEFAULT NULL COMMENT '令牌',
  `address` mediumtext COMMENT '用户收货地址',
  `defaultAddressId` varchar(100) DEFAULT NULL COMMENT '默认地址ID',
  `type` int(11) DEFAULT NULL COMMENT '用户类型',
  `loginTime` bigint(20) DEFAULT NULL COMMENT '最新登录时间',
  `registerTime` bigint(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`,`uid`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_uid` (`uid`) USING BTREE,
  KEY `index_username` (`username`) USING BTREE,
  KEY `index_token` (`token`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
