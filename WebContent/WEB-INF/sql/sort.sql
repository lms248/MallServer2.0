/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2016-09-21 10:17:37
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
INSERT INTO `sort` VALUES ('101', '1', '连衣裙', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('102', '1', '衬衫', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('103', '1', '半身裙', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('104', '1', '裤子', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('105', '1', '卫衣', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('106', '1', '风衣', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('107', '1', '高跟鞋', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('108', '1', '坡跟鞋', '1', '2', '商品二级分类');
INSERT INTO `sort` VALUES ('10001', '0', '大聚惠', '2', '1', '活动一级分类');
INSERT INTO `sort` VALUES ('10002', '0', '海外购', '2', '1', '活动一级分类');
INSERT INTO `sort` VALUES ('10003', '0', '超市百货', '2', '1', '活动一级分类');
INSERT INTO `sort` VALUES ('10004', '0', '厂家直销', '2', '1', '活动一级分类');
INSERT INTO `sort` VALUES ('10005', '0', '美食娱乐', '2', '1', '活动一级分类');
INSERT INTO `sort` VALUES ('10006', '0', 'banner', '2', '1', '活动一级分类');
INSERT INTO `sort` VALUES ('10007', '0', '精选促销', '2', '1', '活动商品精选促销');
INSERT INTO `sort` VALUES ('100001', '10001', '一元抢购', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100002', '10001', '九元九专场', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100003', '10001', '开业大聚惠', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100004', '10002', '手机数码', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100005', '10002', '母婴用品', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100006', '10002', '美妆洗护', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100007', '10002', '男装', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100008', '10002', '电脑办公', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100009', '10003', '休闲零食', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100010', '10003', '粗粮干货', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100011', '10003', '进口粮食', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100012', '10003', '牛奶冲饮', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100013', '10003', '生鲜蔬菜', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100014', '10004', '女装', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100015', '10004', '女鞋', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100016', '10004', '母婴用品', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100017', '10004', '美妆', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100018', '10004', '洗护', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100019', '10004', '男装', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100020', '10005', '休闲零食', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100021', '10005', '粗粮干货', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100022', '10005', '进口粮食', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100023', '10005', '牛奶冲饮', '2', '2', '活动二级分类');
INSERT INTO `sort` VALUES ('100024', '10005', '生鲜蔬菜', '2', '2', '活动二级分类');
