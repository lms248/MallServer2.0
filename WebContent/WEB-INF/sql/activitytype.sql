/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-11-15 11:49:55
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of activitytype
-- ----------------------------
INSERT INTO `activitytype` VALUES ('1', 'banner', '1', '首页商品分类');
INSERT INTO `activitytype` VALUES ('2', '大聚惠', '2', '首页商品分类');
INSERT INTO `activitytype` VALUES ('3', '海外购', '2', '首页商品分类');
INSERT INTO `activitytype` VALUES ('4', '超市百货', '2', '首页商品分类');
INSERT INTO `activitytype` VALUES ('5', '厂家直销', '2', '首页商品分类');
INSERT INTO `activitytype` VALUES ('6', '美食娱乐', '2', '首页商品分类');
INSERT INTO `activitytype` VALUES ('7', '精品促销', '3', '首页商品分类');
