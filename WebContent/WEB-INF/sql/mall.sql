/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-22 17:57:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `activityId` bigint(20) NOT NULL COMMENT '活动ID',
  `goodsId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `sortId` int(11) DEFAULT NULL COMMENT '分类ID',
  `title` varchar(500) DEFAULT NULL COMMENT '活动标题',
  `mark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`activityId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activitytype
-- ----------------------------
DROP TABLE IF EXISTS `activitytype`;
CREATE TABLE `activitytype` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `type` varchar(255) DEFAULT NULL COMMENT '类别',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `cartId` bigint(20) NOT NULL COMMENT '购物车ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `goodsList` mediumtext COMMENT '商品列表信息',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`cartId`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `communityId` bigint(20) DEFAULT NULL COMMENT '社区ID',
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `details` text COMMENT '描述',
  `imageList` varchar(1000) DEFAULT NULL COMMENT '图片列表',
  `thumbList` varchar(1000) DEFAULT NULL COMMENT '缩略图列表',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `feedbackId` bigint(20) NOT NULL COMMENT '反馈ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `info` varchar(1000) DEFAULT NULL COMMENT '反馈内容',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`feedbackId`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `logo` varchar(255) DEFAULT NULL COMMENT '商品Logo',
  `logoThumb` varchar(255) DEFAULT NULL COMMENT '商品Logo缩略图',
  `imageList` text COMMENT '图片列表',
  `thumbList` text COMMENT '图片缩略图',
  `curPrice` double DEFAULT NULL COMMENT '当前价格',
  `prePrice` double DEFAULT NULL COMMENT '原来价格',
  `tags` text COMMENT '属性标记',
  `details` text COMMENT '描述，介绍',
  `sortId` int(11) DEFAULT NULL COMMENT '分类ID',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`goodsId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `shopId` bigint(20) NOT NULL COMMENT '商店ID',
  `name` varchar(255) DEFAULT NULL COMMENT '商店名',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `details` text COMMENT '描述，介绍',
  `logo` varchar(255) DEFAULT NULL COMMENT '店铺logo',
  `logoThumb` varchar(255) DEFAULT NULL COMMENT '店铺logo缩略图',
  `image` varchar(255) DEFAULT NULL COMMENT '背景图片',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '背景缩略图',
  `contactPhone` varchar(255) DEFAULT NULL COMMENT '联系客服电话',
  `type` varchar(255) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`shopId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sort
-- ----------------------------
DROP TABLE IF EXISTS `sort`;
CREATE TABLE `sort` (
  `id` int(11) NOT NULL COMMENT '定义ID',
  `pid` int(11) DEFAULT NULL COMMENT '父类型ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `level` int(11) DEFAULT NULL COMMENT '分类级别',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `sort_id` (`pid`)
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
  `address` mediumtext COMMENT '用户收货地址',
  `defaultAddressId` bigint(20) DEFAULT NULL COMMENT '默认地址ID',
  `loginTime` bigint(20) DEFAULT NULL COMMENT '最新登录时间',
  `registerTime` bigint(20) DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`,`uid`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
