/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-07-25 20:49:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `goodsid` bigint(20) NOT NULL COMMENT '商品ID',
  `shopid` bigint(20) DEFAULT NULL COMMENT '商店ID',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `imagelist` varchar(1000) DEFAULT NULL COMMENT '图片列表',
  `cur_price` double DEFAULT NULL COMMENT '当前价格',
  `pre_price` double DEFAULT NULL COMMENT '原来价格',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `size` double DEFAULT NULL COMMENT '尺寸',
  `introduction` text COMMENT '介绍',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `time` bigint(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`,`goodsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordeid` bigint(20) NOT NULL,
  `uid` bigint(20) DEFAULT NULL,
  `goodsid` bigint(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`ordeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `shopid` bigint(20) NOT NULL COMMENT '商店ID',
  `name` varchar(255) DEFAULT NULL COMMENT '商店名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `time` bigint(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`,`shopid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `token` varchar(255) DEFAULT NULL COMMENT '令牌',
  `time注册时间` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`uid`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
