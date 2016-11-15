/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:50:29
*/

SET FOREIGN_KEY_CHECKS=0;

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
