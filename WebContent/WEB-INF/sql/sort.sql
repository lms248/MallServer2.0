/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-14 17:11:52
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of sort
-- ----------------------------
INSERT INTO `sort` VALUES ('1', '0', '潮流女装', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('2', '0', '潮流男装', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('3', '0', '居家小商品', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('4', '0', '品牌鞋类', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('5', '0', '皮具箱包', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('6', '0', '内衣针织', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('7', '0', '儿童玩具', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('8', '0', '母婴用品', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('9', '0', '电子数码', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('10', '0', '办公用品', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('11', '0', '体育用品', '1', '1', '商品一级分类');
INSERT INTO `sort` VALUES ('1001', '1', '连衣裙', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1002', '1', '衬衫', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1003', '1', '半身裙', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1004', '1', '裤子', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1005', '1', '卫衣', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1006', '1', '风衣', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1007', '1', '高跟鞋', '2', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('1008', '1', '坡跟鞋', '2', '2', '商品二级分类');
