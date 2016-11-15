/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:48:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `activityId` varchar(100) NOT NULL COMMENT '活动ID',
  `goodsId` varchar(100) DEFAULT NULL COMMENT '商品ID',
  `sortId` int(11) DEFAULT NULL COMMENT '分类ID',
  `title` varchar(500) DEFAULT NULL COMMENT '活动标题',
  `mark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`activityId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_activityId` (`activityId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
  `cartId` varchar(100) NOT NULL COMMENT '购物车ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `goodsList` mediumtext COMMENT '商品列表信息',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`cartId`,`uid`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_cartId` (`cartId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `collectId` varchar(100) NOT NULL COMMENT '收藏ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `goodsId` varchar(100) DEFAULT NULL COMMENT '商品ID',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`collectId`,`uid`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_collectId` (`collectId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `commentId` varchar(255) NOT NULL COMMENT '评论ID',
  `goodsId` varchar(255) DEFAULT NULL COMMENT '商品ID',
  `orderId` varchar(255) DEFAULT NULL COMMENT '订单ID',
  `uid` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `content` text COMMENT '评论内容',
  `star` int(11) DEFAULT NULL COMMENT '评论星级',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`commentId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_commentId` (`commentId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `communityId` varchar(100) DEFAULT NULL COMMENT '社区ID',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `details` text COMMENT '描述',
  `imageList` varchar(1000) DEFAULT NULL COMMENT '图片列表',
  `thumbList` varchar(1000) DEFAULT NULL COMMENT '缩略图列表',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_communityId` (`communityId`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `feedbackId` varchar(100) NOT NULL COMMENT '反馈ID',
  `uid` varchar(100) NOT NULL COMMENT '用户ID',
  `info` varchar(1000) DEFAULT NULL COMMENT '反馈内容',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`feedbackId`,`uid`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_feedbackId` (`feedbackId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `goodsId` varchar(100) NOT NULL COMMENT '商品ID',
  `shopId` varchar(100) DEFAULT NULL COMMENT '商店ID',
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
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`,`goodsId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_goodsId` (`goodsId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `messageId` varchar(100) DEFAULT NULL COMMENT '消息ID',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_messageId` (`messageId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_payId` (`payId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `shopId` varchar(100) NOT NULL COMMENT '商店ID',
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
  PRIMARY KEY (`id`,`shopId`),
  UNIQUE KEY `index_id` (`id`) USING BTREE,
  UNIQUE KEY `index_shopId` (`shopId`) USING BTREE,
  KEY `index_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
  `logo` varchar(255) DEFAULT NULL COMMENT '分类logo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
