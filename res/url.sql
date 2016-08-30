/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50542
 Source Host           : localhost
 Source Database       : blog

 Target Server Type    : MySQL
 Target Server Version : 50542
 File Encoding         : utf-8

 Date: 08/30/2016 14:26:11 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `url`
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `controller` varchar(255) NOT NULL,
  `view` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `url`
-- ----------------------------
BEGIN;
INSERT INTO `url` VALUES ('1', 'people', 'create', 'org.yoqu.cms.admin.modules.people.Controller', null, '/admin/people/create', '0'), ('2', 'people', 'index', 'org.yoqu.cms.admin.modules.people.Controller', null, '/admin/people', '0'), ('3', 'people', 'edit', 'org.yoqu.cms.admin.modules.people.Controller', null, '/admin/people/edit', '0'), ('4', 'index', 'index', 'org.yoqu.cms.front.modules.index.IndexController', '/front/index', '/index', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
