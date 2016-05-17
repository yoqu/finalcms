/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50613
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50613
File Encoding         : 65001

Date: 2016-05-17 15:27:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `content` longtext,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `conmentuid` (`uid`),
  KEY `commnetnid` (`nid`),
  CONSTRAINT `commnetnid` FOREIGN KEY (`nid`) REFERENCES `node` (`id`) ON UPDATE NO ACTION,
  CONSTRAINT `conmentuid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeid` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `value` varchar(255) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dictionarytype` (`typeid`),
  CONSTRAINT `dictionarytype` FOREIGN KEY (`typeid`) REFERENCES `dictionary_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES ('1', '1', 'site_name', 'final cms系统', 'final cms系统', '0');
INSERT INTO `dictionary` VALUES ('2', '1', 'page_size', '10', 'pagesize', '0');

-- ----------------------------
-- Table structure for dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_type`;
CREATE TABLE `dictionary_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dictionary_type
-- ----------------------------
INSERT INTO `dictionary_type` VALUES ('1', 'system', '系统字典', '0');
INSERT INTO `dictionary_type` VALUES ('2', 'node', 'node字典', '0');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `fid` int(11) NOT NULL,
  `upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `fid` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `typeid` (`type`),
  CONSTRAINT `typeid` FOREIGN KEY (`type`) REFERENCES `menu_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for menu_type
-- ----------------------------
DROP TABLE IF EXISTS `menu_type`;
CREATE TABLE `menu_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_type
-- ----------------------------
INSERT INTO `menu_type` VALUES ('1', '主菜单', '0');

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `module_url` varchar(255) DEFAULT NULL,
  `is_delete` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of module
-- ----------------------------

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 NOT NULL,
  `content` longtext CHARACTER SET utf8 NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `tags` varchar(255) CHARACTER SET utf8 NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nodeuid` (`uid`),
  CONSTRAINT `nodeuid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of node
-- ----------------------------
INSERT INTO `node` VALUES ('1', '1', 'first Blog', 'Blog Blog Blog', '2016-04-01 00:00:00', '2016-05-04 00:00:00', '', '0');
INSERT INTO `node` VALUES ('2', '1', 'qweq', 'nodenodnenodenodneno\r\nasdajsdiz', '2016-05-12 09:48:19', '2016-05-12 09:48:19', 'one,two,three', '0');
INSERT INTO `node` VALUES ('3', '1', 'second node', 'nodenodnenodenodneno\r\nasdajsdizz啊', '2016-05-12 00:00:00', '2016-05-02 00:00:00', 'one,two,three', '1');
INSERT INTO `node` VALUES ('4', '1', 'second node', 'nihao', '2016-05-12 00:00:00', '2016-05-12 00:00:00', 'one,two,three', '1');
INSERT INTO `node` VALUES ('5', '1', 'markdown Node', '#this is MarkDown Node\r\n- one\r\n- two\r\n- three\r\n[Blog](http://www.yoqu.org \"Blog\")', '2016-05-12 14:21:33', '2016-05-12 14:21:33', 'Blog', '1');
INSERT INTO `node` VALUES ('6', '1', 'Final CMS发布啦', '#finalcms\r\n本项目基于java极速开发框架 **[JFinal](http://git.oschina.net/jfinal/jfinal)** \r\n>简化业务员代码量，极速的cms框架，java插件热插拔式支持系统工作,可任意定制系统的任意页面，插件只需要编译，界面安装即可使用.和系统插件完全分离\r\n\r\n系统核心功能包括  \r\n1. **前台主题嵌套**   \r\n2. **页面样式定制**   \r\n3. **后台人员权限管理**    \r\n4. **插件管理**  \r\n##项目功能数据库设计图 ##\r\n![design_picure](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/CMS_Design_picture.png)\r\n\r\n##项目简介\r\n\r\n\r\n## 如何使用\r\n#### 设置IDE\r\n1. 下载源代码\r\n2. 使用idea打开项目(项目使用idea 15.05的版本)\r\n3. 主菜单的run选项卡设置\r\n![选项卡设置](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/menu_editconfiruration.png)\r\n4. 设置标红地方的参数\r\n![runConfiguration](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/runConfiguration.png)\r\n>启动类为 __org.yoqu.cms.core.config.StartFinalCMS__\r\n\r\n#### 导入数据到数据库\r\n1. 项目中docs目录包含finalcms.sql文件，将其导入你的数据库中，数据库使用的是mysql。\r\n2. 在res目录的database_config.txt文件中配置数据库链接信息\r\n\r\n#### 最后\r\n* 快捷键shift + F10运行项目.\r\n* 浏览器输入localhost即可访问.\r\n\r\n##FAQ\r\n>* **问**：数据库字段修改如何同步到项目中.\r\n* **答**：运行核心util包中的ModelGenerator类重新生成（不推荐用户改动系统核心包）\r\n\r\n*********\r\n\r\n>* **问**：运行项目显示80端口被占用.\r\n* **答**：1.关闭占用80端口的应用程序 2.在核心包的config包下StartFinalCMS类中修改START_PORT修改为你想要的启动端口\r\n\r\n## 其他\r\n\r\n[Hook文档](https://github.com/yoqu/finalcms/blob/master/docs/Hook文档.md)\r\n\r\n##关于\r\n项目发起者: _yoqu_\r\n', '2016-05-12 16:46:18', '2016-05-12 16:46:18', 'Final,CMS', '0');
INSERT INTO `node` VALUES ('7', '1', 'Hello World', '# \"Hello World\"\r\n# \'Hello java\'\r\n```\r\npublic void main(){\r\nsay();\r\n}\r\n```', '2016-05-13 10:26:22', '2016-05-13 10:26:22', 'Hello', '0');
INSERT INTO `node` VALUES ('8', '1', 'saasa', '请输入内容...asa', '2016-05-16 13:18:58', '2016-05-16 13:19:03', 'sda,sd,zxcccc,a', '0');
INSERT INTO `node` VALUES ('9', '1', 'nothing', '#阿三的请输入内容...', '2016-05-16 15:18:11', '2016-05-16 15:18:18', 'nicai', '0');
INSERT INTO `node` VALUES ('10', '1', 'why', '111aa	z请输入内容...', '2016-05-16 15:27:55', '2016-05-16 15:27:55', 'a', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '0');
INSERT INTO `role` VALUES ('2', 'reporter', '0');
INSERT INTO `role` VALUES ('3', 'editer', '0');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `module` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_permission` (`rid`),
  CONSTRAINT `role_permission` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('8', '1', 'people', 'create', '0');
INSERT INTO `role_permission` VALUES ('9', '1', 'people', 'index', '0');
INSERT INTO `role_permission` VALUES ('10', '2', 'people', 'create', '0');
INSERT INTO `role_permission` VALUES ('11', '3', 'people', 'create', '0');
INSERT INTO `role_permission` VALUES ('12', '3', 'people', 'index', '0');

-- ----------------------------
-- Table structure for url
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of url
-- ----------------------------
INSERT INTO `url` VALUES ('1', 'people', 'create', '/admin/people/create', '0');
INSERT INTO `url` VALUES ('2', 'people', 'index', '/admin/people', '0');
INSERT INTO `url` VALUES ('3', 'people', 'edit', '/admin/people/edit', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `createDate` datetime NOT NULL,
  `lastDate` datetime DEFAULT NULL,
  `rid` int(11) NOT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role` (`rid`),
  CONSTRAINT `user_role` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', null, 'yoqulin', '1545', 'yoqulin@qq.com', 'E1:0A:DC:39:49:BA:59:AB:BE:56:E0:57:F2:0F:88:3E', '2016-04-18 16:51:12', '2016-05-17 11:16:34', '1', '0');
INSERT INTO `user` VALUES ('2', 'zia', null, 'yzuaq', '123123', 'yzxu@ema.com', '123456789', '2016-04-20 17:26:06', '2016-04-20 17:26:06', '1', '1');
INSERT INTO `user` VALUES ('3', 'tony', null, 'touni', '192281', 'ajzua@qq.com', 'E1:0A:DC:39:49:BA:59:AB:BE:56:E0:57:F2:0F:88:3E', '2016-05-04 16:38:26', '2016-05-09 13:38:51', '2', '0');
