/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-08-09 21:08:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `communityId` int(11) DEFAULT NULL COMMENT '社区ID',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `desc` varchar(1000) DEFAULT NULL COMMENT '描述',
  `imageList` varchar(1000) DEFAULT NULL COMMENT '图片列表',
  `time` bigint(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `goodsId` bigint(20) NOT NULL COMMENT '商品ID',
  `shopId` bigint(20) DEFAULT NULL COMMENT '商店ID',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `imageList` varchar(1000) DEFAULT NULL COMMENT '图片列表',
  `curPrice` double DEFAULT NULL COMMENT '当前价格',
  `prePrice` double DEFAULT NULL COMMENT '原来价格',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `size` double DEFAULT NULL COMMENT '尺寸',
  `desc` text COMMENT '介绍',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `time` bigint(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`,`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `ordeId` bigint(20) NOT NULL COMMENT '订单ID',
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goodsId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `size` double DEFAULT NULL COMMENT '尺寸',
  `time` bigint(20) DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`id`,`ordeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `shopId` bigint(20) NOT NULL COMMENT '商店ID',
  `name` varchar(255) DEFAULT NULL COMMENT '商店名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `time` bigint(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`,`shopId`)
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
  `time` bigint(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`,`uid`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
