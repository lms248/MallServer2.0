/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-08-20 14:36:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sort
-- ----------------------------
DROP TABLE IF EXISTS `sort`;
CREATE TABLE `sort` (
  `id` int(11) NOT NULL COMMENT '定义ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `level` int(11) DEFAULT NULL COMMENT '分类级别',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sort
-- ----------------------------
INSERT INTO `sort` VALUES ('1', '大聚惠', '1', '1', '首页商品分类');
INSERT INTO `sort` VALUES ('2', '海外购', '1', '1', '首页商品分类');
INSERT INTO `sort` VALUES ('3', '超市百货', '1', '1', '首页商品分类');
INSERT INTO `sort` VALUES ('4', '厂家直销', '1', '1', '首页商品分类');
INSERT INTO `sort` VALUES ('5', '美食娱乐', '1', '1', '首页商品分类');
INSERT INTO `sort` VALUES ('6', '潮流女装', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('7', '潮流男装', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('8', '居家小商品', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('9', '品牌鞋类', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('10', '皮具箱包', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('11', '内衣针织', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('12', '儿童玩具', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('13', '母婴用品', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('14', '电子数码', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('15', '办公用品', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('16', '体育用品', '2', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('17', null, null, null, null);
INSERT INTO `sort` VALUES ('18', null, null, null, null);
INSERT INTO `sort` VALUES ('19', null, null, null, '');
INSERT INTO `sort` VALUES ('20', null, null, null, null);
