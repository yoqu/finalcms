/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50630
Source Host           : 10.210.36.183:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2016-05-27 15:42:40
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
INSERT INTO `dictionary` VALUES ('1', '1', 'site_name', 'yoqu的系统', 'final cms系统', '0');
INSERT INTO `dictionary` VALUES ('2', '1', 'page_size', '20', 'pagesize', '0');

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
  `name` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `fid` int(11) NOT NULL,
  `upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('21', null, null, '8b4a353233393135363237070e1.jpg', 'node', '17', '2016-05-19 10:24:06');
INSERT INTO `file` VALUES ('22', null, null, '8b4a353233393135363237070e2.jpg', null, '-1', '2016-05-19 10:26:08');
INSERT INTO `file` VALUES ('32', null, null, '8b4a353233393135363237070e3.jpg', 'node', '21', '2016-05-19 10:35:44');
INSERT INTO `file` VALUES ('33', '8b4a353233393135363237070e4.jpg', '5444', 'E:\\workspace\\fincalcms\\web\\upload', 'node', '-1', '2016-05-19 15:30:45');
INSERT INTO `file` VALUES ('34', '头像.jpg', '27951', 'E:\\workspace\\fincalcms\\web\\upload', null, '-1', '2016-05-19 15:32:54');
INSERT INTO `file` VALUES ('35', '头像1.jpg', '27951', 'E:\\workspace\\fincalcms\\web\\upload', null, '-1', '2016-05-19 15:33:39');
INSERT INTO `file` VALUES ('36', '头像2.jpg', '27951', 'E:\\workspace\\fincalcms\\web\\upload', 'node', '24', '2016-05-19 15:35:45');
INSERT INTO `file` VALUES ('37', '8b4a353233393135363237070e5.jpg', '5444', 'E:\\workspace\\fincalcms\\web\\upload', null, '-1', '2016-05-19 15:37:50');
INSERT INTO `file` VALUES ('39', '森林.jpg', '1294604', 'E:\\workspace\\fincalcms\\web\\upload', 'node', '25', '2016-05-19 16:26:46');
INSERT INTO `file` VALUES ('42', 'idea.desktop', '154', '/media/yoqu/document/workspace/fincalcms/web/upload', 'node', '32', '2016-05-24 14:18:19');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `fid` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `typeid` (`type`),
  CONSTRAINT `typeid` FOREIGN KEY (`type`) REFERENCES `menu_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'index', null, '1', '/admin/dashboard', '-1', '0');
INSERT INTO `menu` VALUES ('2', 'last', null, '1', '/admin/last', '-1', '0');
INSERT INTO `menu` VALUES ('3', 'midlle', null, '1', '/admin/middle', '-1', '0');
INSERT INTO `menu` VALUES ('4', 'chidone', null, '1', '/admin/one', '-1', '0');
INSERT INTO `menu` VALUES ('5', 'childtwo', null, '1', '/admin/two', '-1', '0');
INSERT INTO `menu` VALUES ('6', '第二页面', null, '3', '/admin/dashboard', '-1', '0');
INSERT INTO `menu` VALUES ('7', '第三页面', null, '3', '/admin/about', '-1', '0');
INSERT INTO `menu` VALUES ('8', '第二页面儿子', null, '3', '/admin/erzi', '7', '0');
INSERT INTO `menu` VALUES ('9', '第二页面儿子的儿子', null, '3', '/erzi/erzi', '8', '0');
INSERT INTO `menu` VALUES ('10', '面板', 'mdi-action-dashboard', '4', '/admin/dashboard', '-1', '0');
INSERT INTO `menu` VALUES ('11', '菜单', 'mdi-navigation-menu', '4', '/admin/menu', '-1', '0');
INSERT INTO `menu` VALUES ('12', '权限', 'mdi-action-perm-contact-cal', '4', '/admin/role', '-1', '0');
INSERT INTO `menu` VALUES ('13', '内容管理', 'mdi-content-drafts', '4', '/admin/node', '-1', '0');
INSERT INTO `menu` VALUES ('14', '用户管理', 'mdi-action-account-circle', '4', '/admin/people', '15', '0');
INSERT INTO `menu` VALUES ('15', '用户', 'mdi-action-account-circle', '4', '/admin/people', '-1', '0');
INSERT INTO `menu` VALUES ('16', '添加用户', null, '4', '/admin/people/create', '15', '0');
INSERT INTO `menu` VALUES ('17', '添加菜单', null, '4', '/admin/menu/create', '11', '0');
INSERT INTO `menu` VALUES ('18', '菜单管理', null, '4', '/admin/menu', '11', '0');
INSERT INTO `menu` VALUES ('19', '添加菜单项', null, '4', '/admin/menu/item/create', '11', '0');
INSERT INTO `menu` VALUES ('20', '系统设置', 'mdi-action-settings', '4', '/admin/setting', '-1', '0');
INSERT INTO `menu` VALUES ('21', '变量设置', null, '4', '/admin/setting/systemVariable', '20', '0');

-- ----------------------------
-- Table structure for menu_type
-- ----------------------------
DROP TABLE IF EXISTS `menu_type`;
CREATE TABLE `menu_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_type
-- ----------------------------
INSERT INTO `menu_type` VALUES ('1', '主菜单', '0');
INSERT INTO `menu_type` VALUES ('3', '修改二级菜单', '0');
INSERT INTO `menu_type` VALUES ('4', '管理左侧菜单', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of node
-- ----------------------------
INSERT INTO `node` VALUES ('1', '1', 'first Blog', 'Blog Blog Blog', '2016-04-01 00:00:00', '2016-05-04 00:00:00', '', '1');
INSERT INTO `node` VALUES ('2', '1', 'qweq', 'nodenodnenodenodneno\r\nasdajsdiz', '2016-05-12 09:48:19', '2016-05-12 09:48:19', 'one,two,three', '1');
INSERT INTO `node` VALUES ('3', '1', 'second node', 'nodenodnenodenodneno\r\nasdajsdizz啊', '2016-05-12 00:00:00', '2016-05-02 00:00:00', 'one,two,three', '1');
INSERT INTO `node` VALUES ('4', '1', 'second node', 'nihao', '2016-05-12 00:00:00', '2016-05-12 00:00:00', 'one,two,three', '1');
INSERT INTO `node` VALUES ('5', '1', 'markdown Node', '#this is MarkDown Node\r\n- one\r\n- two\r\n- three\r\n[Blog](http://www.yoqu.org \"Blog\")', '2016-05-12 14:21:33', '2016-05-12 14:21:33', 'Blog', '1');
INSERT INTO `node` VALUES ('6', '1', 'Final CMS发布啦', '#finalcms\r\n本项目基于java极速开发框架 **[JFinal](http://git.oschina.net/jfinal/jfinal)** \r\n>简化业务员代码量，极速的cms框架，java插件热插拔式支持系统工作,可任意定制系统的任意页面，插件只需要编译，界面安装即可使用.和系统插件完全分离\r\n\r\n系统核心功能包括  \r\n1. **前台主题嵌套**   \r\n2. **页面样式定制**   \r\n3. **后台人员权限管理**    \r\n4. **插件管理**  \r\n##项目功能数据库设计图 ##\r\n![design_picure](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/CMS_Design_picture.png)\r\n\r\n##项目简介\r\n\r\n\r\n## 如何使用\r\n#### 设置IDE\r\n1. 下载源代码\r\n2. 使用idea打开项目(项目使用idea 15.05的版本)\r\n3. 主菜单的run选项卡设置\r\n![选项卡设置](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/menu_editconfiruration.png)\r\n4. 设置标红地方的参数\r\n![runConfiguration](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/runConfiguration.png)\r\n>启动类为 __org.yoqu.cms.core.config.StartFinalCMS__\r\n\r\n#### 导入数据到数据库\r\n1. 项目中docs目录包含finalcms.sql文件，将其导入你的数据库中，数据库使用的是mysql。\r\n2. 在res目录的database_config.txt文件中配置数据库链接信息\r\n\r\n#### 最后\r\n* 快捷键shift + F10运行项目.\r\n* 浏览器输入localhost即可访问.\r\n\r\n##FAQ\r\n>* **问**：数据库字段修改如何同步到项目中.\r\n* **答**：运行核心util包中的ModelGenerator类重新生成（不推荐用户改动系统核心包）\r\n\r\n*********\r\n\r\n>* **问**：运行项目显示80端口被占用.\r\n* **答**：1.关闭占用80端口的应用程序 2.在核心包的config包下StartFinalCMS类中修改START_PORT修改为你想要的启动端口\r\n\r\n## 其他\r\n\r\n[Hook文档](https://github.com/yoqu/finalcms/blob/master/docs/Hook文档.md)\r\n\r\n##关于\r\n项目发起者: _yoqu_\r\n', '2016-05-12 16:46:18', '2016-05-12 16:46:18', 'Final,CMS', '0');
INSERT INTO `node` VALUES ('7', '1', 'Hello World', '# \"Hello World\"\r\n# \'Hello java\'\r\n```\r\npublic void main(){\r\nsay();\r\n}\r\n```', '2016-05-13 10:26:22', '2016-05-13 10:26:22', 'Hello', '1');
INSERT INTO `node` VALUES ('8', '1', 'saasa', '请输入内容...asa', '2016-05-16 13:18:58', '2016-05-16 13:19:03', 'sda,sd,zxcccc,a', '1');
INSERT INTO `node` VALUES ('9', '1', 'nothing', '#阿三的请输入内容...', '2016-05-16 15:18:11', '2016-05-16 15:18:18', 'nicai', '1');
INSERT INTO `node` VALUES ('10', '1', 'why', '111aa	z请输入内容...', '2016-05-16 15:27:55', '2016-05-16 15:27:55', 'a', '1');
INSERT INTO `node` VALUES ('11', '1', '你猜', '请输入内容...', '2016-05-18 17:29:08', '2016-05-18 17:29:08', 'nicaragua', '1');
INSERT INTO `node` VALUES ('12', '1', '再来一次', '请输入内容...', '2016-05-18 17:30:22', '2016-05-18 17:30:22', '阿三的', '1');
INSERT INTO `node` VALUES ('13', '1', 'upload file', '请输入内容...', '2016-05-19 09:26:48', '2016-05-19 09:26:48', 'file,upload', '1');
INSERT INTO `node` VALUES ('14', '1', 'upload file2', '请输入内容...', '2016-05-19 09:29:34', '2016-05-19 09:29:34', 'upload,file', '1');
INSERT INTO `node` VALUES ('17', '1', '文件上传', '文件上传请输入内容...', '2016-05-19 10:24:49', '2016-05-19 10:24:49', '上传,一二', '1');
INSERT INTO `node` VALUES ('21', '1', '上传测是', '请输入内容...', '2016-05-19 10:35:58', '2016-05-19 10:35:58', '测试', '1');
INSERT INTO `node` VALUES ('22', '1', '新版上传文件测试', '测试内容请输入内容...', '2016-05-19 15:30:55', '2016-05-19 15:30:55', '测试', '1');
INSERT INTO `node` VALUES ('23', '1', 'tags', '请输入内容...啊他说', '2016-05-19 15:33:48', '2016-05-19 15:33:48', '啊', '1');
INSERT INTO `node` VALUES ('24', '1', 'ziizx', '请输入内容...zxczxcxz', '2016-05-19 15:35:50', '2016-05-19 15:35:50', 'asda', '1');
INSERT INTO `node` VALUES ('25', '1', 'new Page', '请输入内容...', '2016-05-19 16:26:49', '2016-05-19 16:26:49', 'page', '1');
INSERT INTO `node` VALUES ('26', '1', 'Git日常使用记录', '#other\r\n##Git中启用大小写敏感\r\n```\r\ngit config core.ignorecase false \r\n```\r\n', '2016-05-19 16:55:50', '2016-05-19 16:55:50', 'Git', '0');
INSERT INTO `node` VALUES ('27', '1', 'nginx服务器安装及配置文件详解', '>转载自[sean](http://seanlook.com/2015/05/17/nginx-install-and-config/)\r\n\r\n********\r\n\r\n>nginx在工作中已经有好几个环境在使用了，每次都是重新去网上扒博客，各种编译配置，今天自己也整理一份安装文档和nginx.conf配置选项的说明，留作以后参考。像负载均衡配置（包括健康检查）、缓存（包括清空缓存）配置实例，请参考 http://seanlook.com/2015/05/17/nginx-install-and-config ，ssl加密请参考 http://seanlook.com/2015/05/28/nginx-ssl/ 。\r\n\r\n#1. 安装nginx\r\n##1.1 选择稳定版本\r\n我们编译安装nginx来定制自己的模块，机器CentOS 6.2 x86_64。首先安装缺少的依赖包：\r\n``` \r\nyum -y install gcc gcc-c++ make libtool zlib zlib-devel openssl openssl-devel pcre pcre-devel\r\n```\r\n这些软件包如果yum上没有的话可以下载源码来编译安装，只是要注意编译时默认安装的目录，确保下面在安装nginx时能够找到这些动态库文件（ldconfig）。\r\n\r\n从 http://nginx.org/en/download.html 下载稳定版nginx-1.6.3.tar.gz到/usr/local/src下解压。\r\n\r\n为了后续准备我们另外下载2个插件模块：**nginx_upstream_check_module-0.3.0.tar.gz **—— 检查后端服务器的状态，***nginx-goodies-nginx-sticky-module-ng-bd312d586752.tar.gz**（建议在*/usr/local/src*下解压后将目录重命名为**nginx-sticky-module-ng-1.2.5**） —— 后端做负载均衡解决session sticky问题（与upstream_check模块结合使用需要另外打补丁，请参考nginx负载均衡配置实战）。\r\n\r\n请注意插件与nginx的版本兼容问题，一般插件越新越好，nginx不用追新，稳定第一。nginx-1.4.7，nginx-sticky-module-1.1，nginx_upstream_check_module-0.2.0，这个搭配也没问题。sticky-1.1与nginx-1.6版本由于更新没跟上编译出错。（可以直接使用Tengine，默认就包括了这些模块）\r\n\r\n```\r\n[root@cachets nginx-1.6.3]# pwd\r\n/usr/local/src/nginx-1.6.3\r\n[root@cachets nginx-1.6.3]# ./configure --prefix=/usr/local/nginx-1.6 --with-pcre \\\r\n> --with-http_stub_status_module --with-http_ssl_module \\\r\n> --with-http_gzip_static_module --with-http_realip_module \\\r\n> --add-module=../nginx_upstream_check_module-0.3.0\r\n[root@cachets nginx-1.6.3]# make && make install\r\n```\r\n##1.2 常用编译选项说明\r\nnginx大部分常用模块，编译时./configure --help以--without开头的都默认安装。\r\n\r\n**--prefix=PATH** ： 指定nginx的安装目录。默认 /usr/local/nginx\r\n**--conf-path=PATH** ： 设置nginx.conf配置文件的路径。nginx允许使用不同的配置文件启动，通过命令行中的-c选项。默认为prefix/conf/nginx.conf\r\n**--user=name**： 设置nginx工作进程的用户。安装完成后，可以随时在nginx.conf配置文件更改user指令。默认的用户名是nobody。--group=name类似\r\n**--with-pcre** ： 设置PCRE库的源码路径，如果已通过yum方式安装，使用--with-pcre自动找到库文件。使用--with-pcre=PATH时，需要从PCRE网站下载pcre库的源码（版本4.4 - 8.30）并解压，剩下的就交给Nginx的./configure和make来完成。perl正则表达式使用在location指令和 ngx_http_rewrite_module模块中。\r\n**--with-zlib=PATH** ： 指定 zlib（版本1.1.3 - 1.2.5）的源码解压目录。在默认就启用的网络传输压缩模块ngx_http_gzip_module时需要使用zlib 。\r\n**--with-http_ssl_module** ： 使用https协议模块。默认情况下，该模块没有被构建。前提是openssl与openssl-devel已安装\r\n**--with-http_stub_status_module** ： 用来监控 Nginx 的当前状态\r\n**--with-http_realip_module** ： 通过这个模块允许我们改变客户端请求头中客户端IP地址值(例如X-Real-IP 或 X-Forwarded-For)，意义在于能够使得后台服务器记录原始客户端的IP地址\r\n**--add-module=PATH** ： 添加第三方外部模块，如nginx-sticky-module-ng或缓存模块。每次添加新的模块都要重新编译（Tengine可以在新加入module时无需重新编译）\r\n再提供一种编译方案：\r\n```\r\n./configure \\\r\n> --prefix=/usr \\\r\n> --sbin-path=/usr/sbin/nginx \\\r\n> --conf-path=/etc/nginx/nginx.conf \\\r\n> --error-log-path=/var/log/nginx/error.log \\\r\n> --http-log-path=/var/log/nginx/access.log \\\r\n> --pid-path=/var/run/nginx/nginx.pid  \\\r\n> --lock-path=/var/lock/nginx.lock \\   \r\n> --user=nginx \\\r\n> --group=nginx \\\r\n> --with-http_ssl_module \\\r\n> --with-http_stub_status_module \\\r\n> --with-http_gzip_static_module \\\r\n> --http-client-body-temp-path=/var/tmp/nginx/client/ \\\r\n> --http-proxy-temp-path=/var/tmp/nginx/proxy/ \\\r\n> --http-fastcgi-temp-path=/var/tmp/nginx/fcgi/ \\\r\n> --http-uwsgi-temp-path=/var/tmp/nginx/uwsgi \\\r\n> --with-pcre=../pcre-7.8\r\n> --with-zlib=../zlib-1.2.3\r\n```\r\n##1.3 启动关闭nginx\r\n\r\n 检查配置文件是否正确\r\n\r\n```\r\n# /usr/local/nginx-1.6/sbin/nginx -t \r\n# ./sbin/nginx -V     # 可以看到编译选项\r\n## 启动、关闭\r\n# ./sbin/nginx        # 默认配置文件 conf/nginx.conf，-c 指定\r\n# ./sbin/nginx -s stop\r\n或 pkill nginx\r\n## 重启，不会改变启动时指定的配置文件\r\n# ./sbin/nginx -s reload\r\n```\r\n\r\n或 kill -HUP `cat /usr/local/nginx-1.6/logs/nginx.pid`\r\n当然也可以将 nginx 作为系统服务管理，下载 nginx 到/etc/init.d/，修改里面的路径然后赋予可执行权限。\r\n\r\n\r\n```\r\n# service nginx {start|stop|status|restart|reload|configtest}\r\n```\r\n\r\n##1.4 yum安装\r\nyum安装rpm包会比编译安装简单很多，默认会安装许多模块，但缺点是如果你想以后安装第三方模块那就没办法了。\r\n```\r\n# vi /etc/yum.repo.d/nginx.repo \r\n[nginx] \r\nname=nginx repo \r\nbaseurl=http://nginx.org/packages/centos/$releasever/$basearch/ \r\ngpgcheck=0 \r\nenabled=1\r\n```\r\n\r\n剩下的就`yum install nginx`搞定，也可以yum install nginx-1.6.3安装指定版本（前提是你去packages里看到有对应的版本，默认是最新版稳定版）。\r\n\r\n###2. nginx.conf配置文件\r\nNginx配置文件主要分成四部分：main（全局设置）、server（主机设置）、upstream（上游服务器设置，主要为反向代理、负载均衡相关配置）和 location（URL匹配特定位置后的设置），每部分包含若干个指令。main部分设置的指令将影响其它所有部分的设置；server部分的指令主要用于指定虚拟主机域名、IP和端口；upstream的指令用于设置一系列的后端服务器，设置反向代理及后端服务器的负载均衡；location部分用于匹配网页位置（比如，根目录“/”,“/images”,等等）。他们之间的关系式：server继承main，location继承server；upstream既不会继承指令也不会被继承。它有自己的特殊指令，不需要在其他地方的应用。\r\n\r\n当前nginx支持的几个指令上下文：\r\n\r\n####2.1 通用\r\n下面的nginx.conf简单的实现nginx在前端做反向代理服务器的例子，处理js、png等静态文件，jsp等动态请求转发到其它服务器tomcat：\r\n\r\n```\r\nuser  www www;\r\nworker_processes  2;\r\n\r\nerror_log  logs/error.log;\r\n#error_log  logs/error.log  notice;\r\n#error_log  logs/error.log  info;\r\n\r\npid        logs/nginx.pid;\r\n\r\n\r\nevents {\r\n    use epoll;\r\n    worker_connections  2048;\r\n}\r\n\r\n\r\nhttp {\r\n    include       mime.types;\r\n    default_type  application/octet-stream;\r\n\r\n    #log_format  main  \'$remote_addr - $remote_user [$time_local] \"$request\" \'\r\n    #                  \'$status $body_bytes_sent \"$http_referer\" \'\r\n    #                  \'\"$http_user_agent\" \"$http_x_forwarded_for\"\';\r\n\r\n    #access_log  logs/access.log  main;\r\n\r\n    sendfile        on;\r\n    # tcp_nopush     on;\r\n\r\n    keepalive_timeout  65;\r\n\r\n  # gzip压缩功能设置\r\n    gzip on;\r\n    gzip_min_length 1k;\r\n    gzip_buffers    4 16k;\r\n    gzip_http_version 1.0;\r\n    gzip_comp_level 6;\r\n    gzip_types text/html text/plain text/css text/javascript application/json application/javascript application/x-javascript application/xml;\r\n    gzip_vary on;\r\n  \r\n  # http_proxy 设置\r\n    client_max_body_size   10m;\r\n    client_body_buffer_size   128k;\r\n    proxy_connect_timeout   75;\r\n    proxy_send_timeout   75;\r\n    proxy_read_timeout   75;\r\n    proxy_buffer_size   4k;\r\n    proxy_buffers   4 32k;\r\n    proxy_busy_buffers_size   64k;\r\n    proxy_temp_file_write_size  64k;\r\n    proxy_temp_path   /usr/local/nginx/proxy_temp 1 2;\r\n\r\n  # 设定负载均衡后台服务器列表 \r\n    upstream  backend  { \r\n              #ip_hash; \r\n              server   192.168.10.100:8080 max_fails=2 fail_timeout=30s ;  \r\n              server   192.168.10.101:8080 max_fails=2 fail_timeout=30s ;  \r\n    }\r\n\r\n  # 很重要的虚拟主机配置\r\n    server {\r\n        listen       80;\r\n        server_name  itoatest.example.com;\r\n        root   /apps/oaapp;\r\n\r\n        charset utf-8;\r\n        access_log  logs/host.access.log  main;\r\n\r\n        #对 / 所有做负载均衡+反向代理\r\n        location / {\r\n            root   /apps/oaapp;\r\n            index  index.jsp index.html index.htm;\r\n\r\n            proxy_pass        http://backend;  \r\n            proxy_redirect off;\r\n            # 后端的Web服务器可以通过X-Forwarded-For获取用户真实IP\r\n            proxy_set_header  Host  $host;\r\n            proxy_set_header  X-Real-IP  $remote_addr;  \r\n            proxy_set_header  X-Forwarded-For  $proxy_add_x_forwarded_for;\r\n            proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504;\r\n            \r\n        }\r\n\r\n        #静态文件，nginx自己处理，不去backend请求tomcat\r\n        location  ~* /download/ {  \r\n            root /apps/oa/fs;  \r\n            \r\n        }\r\n        location ~ .*\\.(gif|jpg|jpeg|bmp|png|ico|txt|js|css)$   \r\n        {   \r\n            root /apps/oaapp;   \r\n            expires      7d; \r\n        }\r\n       	location /nginx_status {\r\n            stub_status on;\r\n            access_log off;\r\n            allow 192.168.10.0/24;\r\n            deny all;\r\n        }\r\n\r\n        location ~ ^/(WEB-INF)/ {   \r\n            deny all;   \r\n        }\r\n        #error_page  404              /404.html;\r\n\r\n        # redirect server error pages to the static page /50x.html\r\n        #\r\n        error_page   500 502 503 504  /50x.html;\r\n        location = /50x.html {\r\n            root   html;\r\n        }\r\n    }\r\n  ## 其它虚拟主机，server 指令开始\r\n}\r\n```\r\n\r\n###2.2 常用指令说明\r\n####2.2.1 main全局配置\r\n\r\nnginx在运行时与具体业务功能（比如http服务或者email服务代理）无关的一些参数，比如工作进程数，运行的身份等。\r\n\r\n`woker_processes 2`\r\n在配置文件的顶级main部分，worker角色的工作进程的个数，master进程是接收并分配请求给worker处理。这个数值简单一点可以设置为cpu的核数grep ^processor /proc/cpuinfo | wc -l，也是 auto 值，如果开启了ssl和gzip更应该设置成与逻辑CPU数量一样甚至为2倍，可以减少I/O操作。如果nginx服务器还有其它服务，可以考虑适当减少。\r\n\r\n`worker_cpu_affinity`\r\n也是写在main部分。在高并发情况下，通过设置cpu粘性来降低由于多CPU核切换造成的寄存器等现场重建带来的性能损耗。如worker_cpu_affinity 0001 0010 0100 1000; （四核）。\r\n\r\n`worker_connections 2048`\r\n写在events部分。每一个worker进程能并发处理（发起）的最大连接数（包含与客户端或后端被代理服务器间等所有连接数）。nginx作为反向代理服务器，计算公式 最大连接数 = worker_processes * worker_connections/4，所以这里客户端最大连接数是1024，这个可以增到到8192都没关系，看情况而定，但不能超过后面的worker_rlimit_nofile。当nginx作为http服务器时，计算公式里面是除以2。\r\n\r\n`worker_rlimit_nofile 10240`\r\n写在main部分。默认是没有设置，可以限制为操作系统最大的限制65535。\r\n\r\n`use epoll`\r\n写在events部分。在Linux操作系统下，nginx默认使用epoll事件模型，得益于此，nginx在Linux操作系统下效率相当高。同时Nginx在OpenBSD或FreeBSD操作系统上采用类似于epoll的高效事件模型kqueue。在操作系统不支持这些高效模型时才使用select。\r\n\r\n####2.2.2 http服务器\r\n\r\n与提供http服务相关的一些配置参数。例如：是否使用keepalive啊，是否使用gzip进行压缩等。\r\n\r\n`sendfile on`\r\n开启高效文件传输模式，sendfile指令指定nginx是否调用sendfile函数来输出文件，减少用户空间到内核空间的上下文切换。对于普通应用设为 on，如果用来进行下载等应用磁盘IO重负载应用，可设置为off，以平衡磁盘与网络I/O处理速度，降低系统的负载。\r\n\r\n`keepalive_timeout 65 `: 长连接超时时间，单位是秒，这个参数很敏感，涉及浏览器的种类、后端服务器的超时设置、操作系统的设置，可以另外起一片文章了。长连接请求大量小文件的时候，可以减少重建连接的开销，但假如有大文件上传，65s内没上传完成会导致失败。如果设置时间过长，用户又多，长时间保持连接会占用大量资源。\r\n\r\n`send_timeout `: 用于指定响应客户端的超时时间。这个超时仅限于两个连接活动之间的时间，如果超过这个时间，客户端没有任何活动，Nginx将会关闭连接。\r\n\r\n`client_max_body_size 10m`\r\n允许客户端请求的最大单文件字节数。如果有上传较大文件，请设置它的限制值\r\n\r\n`client_body_buffer_size 128k`\r\n缓冲区代理缓冲用户端请求的最大字节数\r\n模块http_proxy：\r\n这个模块实现的是nginx作为反向代理服务器的功能，包括缓存功能（另见文章）\r\n\r\n`proxy_connect_timeout 60`\r\nnginx跟后端服务器连接超时时间(代理连接超时)\r\n`proxy_read_timeout 60`\r\n连接成功后，与后端服务器两个成功的响应操作之间超时时间(代理接收超时)\r\n\r\n`proxy_buffer_size 4k`\r\n设置代理服务器（nginx）从后端realserver读取并保存用户头信息的缓冲区大小，默认与proxy_buffers大小相同，其实可以将这个指令值设的小一点\r\n\r\n`proxy_buffers 4 32k`\r\nproxy_buffers缓冲区，nginx针对单个连接缓存来自后端realserver的响应，网页平均在32k以下的话，这样设置\r\n\r\n`proxy_busy_buffers_size 64k`\r\n高负荷下缓冲大小（proxy_buffers*2）\r\n\r\n`proxy_max_temp_file_size`\r\n当 proxy_buffers 放不下后端服务器的响应内容时，会将一部分保存到硬盘的临时文件中，这个值用来设置最大临时文件大小，默认1024M，它与 proxy_cache 没有关系。大于这个值，将从upstream服务器传回。设置为0禁用。\r\n\r\n`proxy_temp_file_write_size 64k`\r\n当缓存被代理的服务器响应到临时文件时，这个选项限制每次写临时文件的大小。proxy_temp_path（可以在编译的时候）指定写到哪那个目录。\r\n\r\nproxy_pass，proxy_redirect见 location 部分。\r\n\r\n模块http_gzip：\r\n\r\n`gzip on` : 开启gzip压缩输出，减少网络传输。\r\n`gzip_min_length 1k `： 设置允许压缩的页面最小字节数，页面字节数从header头得content-length中进行获取。默认值是20。建议设置成大于1k的字节数，小于1k可能会越压越大。\r\n`gzip_buffers 4 16k `： 设置系统获取几个单位的缓存用于存储gzip的压缩结果数据流。4 16k代表以16k为单位，安装原始数据大小以16k为单位的4倍申请内存。\r\n`gzip_http_version 1.0 `： 用于识别 http 协议的版本，早期的浏览器不支持 Gzip 压缩，用户就会看到乱码，所以为了支持前期版本加上了这个选项，如果你用了 Nginx 的反向代理并期望也启用 Gzip 压缩的话，由于末端通信是 http/1.0，故请设置为 1.0。\r\n`gzip_comp_level 6 `： gzip压缩比，1压缩比最小处理速度最快，9压缩比最大但处理速度最慢(传输快但比较消耗cpu)\r\n`gzip_types `：匹配mime类型进行压缩，无论是否指定,”text/html”类型总是会被压缩的。\r\n`gzip_proxied any `： Nginx作为反向代理的时候启用，决定开启或者关闭后端服务器返回的结果是否压缩，匹配的前提是后端服务器必须要返回包含”Via”的 header头。\r\n`gzip_vary on `： 和http头有关系，会在响应头加个 Vary: Accept-Encoding ，可以让前端的缓存服务器缓存经过gzip压缩的页面，例如，用Squid缓存经过Nginx压缩的数据。。\r\n2.2.3 server虚拟主机\r\n\r\nhttp服务上支持若干虚拟主机。每个虚拟主机一个对应的server配置项，配置项里面包含该虚拟主机相关的配置。在提供mail服务的代理时，也可以建立若干server。每个server通过监听地址或端口来区分。\r\n\r\nlisten\r\n监听端口，默认80，小于1024的要以root启动。可以为listen *:80、listen 127.0.0.1:80等形式。\r\n\r\nserver_name\r\n服务器名，如localhost、www.example.com，可以通过正则匹配。\r\n\r\n模块http_stream\r\n这个模块通过一个简单的调度算法来实现客户端IP到后端服务器的负载均衡，upstream后接负载均衡器的名字，后端realserver以 host:port options; 方式组织在 {} 中。如果后端被代理的只有一台，也可以直接写在 proxy_pass 。\r\n\r\n2.2.4 location\r\n\r\nhttp服务中，某些特定的URL对应的一系列配置项。\r\n\r\n`root /var/www/html`\r\n定义服务器的默认网站根目录位置。如果locationURL匹配的是子目录或文件，root没什么作用，一般放在server指令里面或/下。\r\n\r\n`index index.jsp index.html index.htm`\r\n定义路径下默认访问的文件名，一般跟着root放\r\n\r\n`proxy_pass http:/backend`\r\n请求转向backend定义的服务器列表，即反向代理，对应upstream负载均衡器。也可以proxy_pass http://ip:port。\r\n\r\n```\r\nproxy_redirect off;\r\nproxy_set_header Host $host;\r\nproxy_set_header X-Real-IP $remote_addr;\r\nproxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\r\n```\r\n\r\n这四个暂且这样设，如果深究的话，每一个都涉及到很复杂的内容，也将通过另一篇文章来解读。\r\n\r\n关于location匹配规则的写法，可以说尤为关键且基础的，参考文章 nginx配置location总结及rewrite规则写法;\r\n\r\n###2.3 其它\r\n####2.3.1 访问控制 allow/deny\r\n\r\nNginx 的访问控制模块默认就会安装，而且写法也非常简单，可以分别有多个allow,deny，允许或禁止某个ip或ip段访问，依次满足任何一个规则就停止往下匹配。如：\r\n```\r\nlocation /nginx-status {\r\n  stub_status on;\r\n  access_log off;\r\n#  auth_basic   \"NginxStatus\";\r\n#  auth_basic_user_file   /usr/local/nginx-1.6/htpasswd;\r\n\r\n  allow 192.168.10.100;\r\n  allow 172.29.73.0/24;\r\n  deny all;\r\n}\r\n```\r\n\r\n我们也常用 httpd-devel 工具的 htpasswd 来为访问的路径设置登录密码：\r\n\r\n```\r\n# htpasswd -c htpasswd admin\r\nNew passwd:\r\nRe-type new password:\r\nAdding password for user admin\r\n\r\n# htpasswd htpasswd admin    //修改admin密码\r\n# htpasswd htpasswd sean    //多添加一个认证用户\r\n```\r\n\r\n这样就生成了默认使用CRYPT加密的密码文件。打开上面nginx-status的两行注释，重启nginx生效。\r\n\r\n####2.3.2 列出目录 autoindex\r\n\r\nNginx默认是不允许列出整个目录的。如需此功能，打开nginx.conf文件，在location，server 或 http段中加入autoindex on;，另外两个参数最好也加上去:\r\n\r\nautoindex_exact_size off; 默认为on，显示出文件的确切大小，单位是bytes。改为off后，显示出文件的大概大小，单位是kB或者MB或者GB\r\nautoindex_localtime on;\r\n默认为off，显示的文件时间为GMT时间。改为on后，显示的文件时间为文件的服务器时间\r\n\r\n```\r\nlocation /images {\r\n  root   /var/www/nginx-default/images;\r\n  autoindex on;\r\n  autoindex_exact_size off;\r\n  autoindex_localtime on;\r\n  }\r\n```\r\n\r\n参考\r\n\r\nhttp://liuqunying.blog.51cto.com/3984207/1420556\r\nhttp://nginx.org/en/docs/ngx_core_module.html#worker_cpu_affinity\r\nhttp://wiki.nginx.org/HttpCoreModule#sendfile', '2016-05-19 16:57:35', '2016-05-19 16:57:35', 'nginx,centos', '0');
INSERT INTO `node` VALUES ('28', '1', '中文测试', '中文测试		请输入内容...', '2016-05-19 17:05:01', '2016-05-19 17:05:01', '中文', '1');
INSERT INTO `node` VALUES ('29', '1', 'centos7下搭建tomcat+openjdk+mysql+vsftpd+nginx', '\r\n##安装openjdk\r\n```\r\nyum install java-1.7.0-openjdk* -y  \r\n```\r\n**********************\r\n##安装mysql\r\n>由于CentOS 7 版本将MySQL数据库软件从默认的程序列表中移除，用mariadb代替，所以安装mariabd\r\n\r\n* 使用yum安装mariadb\r\n```\r\nyum install mariadb-server mariadb\r\n```\r\n**操作mariadb的命令是：**\r\n```\r\nsystemctl start mariadb  #启动MariaDB\r\nsystemctl stop mariadb  #停止MariaDB\r\nsystemctl restart mariadb  #重启MariaDB\r\nsystemctl enable mariadb  #设置开机启动\r\n```\r\n* 启动数据库\r\n```\r\nsystemctl start mariadb\r\n```\r\n>默认账号是root，没有密码，所以直接登陆\r\n```\r\nmysql -u root\r\n```\r\n* 配置mysql的编码，打开文件/etc/my.cnf,加上下段代码\r\n```\r\n[mysql]\r\ndefault-character-set =utf8\r\n```\r\n* 创建一个mysql用户,并设置可以远程访问\r\n```\r\ngrant usage on *.* to \'fred\'@\'localhost\' identified by \'fred\';//创建用户fred密码ferd\r\nselect host,user,password from mysql.user where user=\'fred\';//查看记录  \r\ngrant all privileges on *.* to fred@\'%\'identified by \'fred\';//设置可以远程访问\r\n```\r\n\r\n\r\n****************\r\n##安装vsftpd\r\n\r\n* 使用yum安装vsftpd\r\n```\r\nsudo yum install vsftpd\r\n```\r\n* 添加ftp用户\r\n```\r\nadduser userftp   //添加用户\r\npasswd userftp\r\n```\r\n* 禁止用户ssh登录，只能ftp访问\r\n```\r\nusermod -s /sbin/nologin userftp\r\n```\r\n* 配置vsftp \r\n```\r\nsudo vi /etc/vsftpd/vstfpd.conf\r\nanonymous_enable=NO  //关闭匿名访问\r\nlocal_enable=YES  //只能访问自己的主目录\r\nlocal_root=/data/doc //设置主目录\r\nsudo service vsftpd restart //重启\r\nckconfig vsftpd on //设置开机自启动\r\n```\r\n\r\n***********************\r\n##安装tomcat\r\n* 使用get命令下载并解压\r\n```\r\nwget http://mirrors.noc.im/apache/tomcat/tomcat-7/v7.0.69/bin/apache-tomcat-7.0.69.zip\r\nunzip apache-tomcat-7.0.69.zip\r\n```\r\n\r\n******************\r\n##安装nginx\r\n* yum安装\r\n```\r\nyum -y install nginx\r\n```\r\n* 编辑默认配置文件并修改一些参数\r\n```\r\n    gzip  on;\r\n    gzip_min_length 1k;\r\n    gzip_buffers    4 16k;\r\n    gzip_http_version 1.0;\r\n    gzip_comp_level 6;\r\n    gzip_types text/html text/plain text/css text/javascript application/json application/javascript application/x-javascript application/xml;\r\n    gzip_vary on;\r\n    proxy_cache_path /nginx/cache levels=1:2 keys_zone=first:10m inactive=24h max_size=1G; #新建缓存路径与相关属性\r\n    upstream backend { #建立后端tomcat服务器\r\n    server 127.0.0.1:8080 weight=1;\r\n   }\r\n```\r\n* 修改默认的nginx配置.文件路径为**/etc/nginx/nginx.conf**\r\n```\r\n    listen  80;\r\n    #return 500;\r\n    server_name blog.yoqu.org ;\r\n    index index.html index.htm index.jsp;#设定访问的默认首页地址\r\n    root  /web/webapp;#设定网站的资源存放路径\r\n    #charset koi8-r;\r\n    #access_log  /var/log/nginx/log/host.access.log  main;\r\n\r\n    location / {\r\n        #root  /usr/software/tomcat/webapps/finalcms;\r\n        #index  index.html index.htm;\r\n        proxy_pass http://127.0.0.1:8080;\r\n         proxy_redirect off;\r\n            # 后端的Web服务器可以通过X-Forwarded-For获取用户真实IP\r\n            proxy_set_header  Host  $host;\r\n            proxy_set_header  X-Real-IP  $remote_addr;\r\n            proxy_set_header  X-Forwarded-For  $proxy_add_x_forwarded_for;\r\n    }\r\n   location ~ .*\\.(gif|jpg|jpeg|bmp|png|ico|txt|js|css)$\r\n    {\r\n            root /web/webapp;\r\n            #expires      12d;\r\n          proxy_cache first;\r\n    proxy_cache_valid 200 24h;\r\n    proxy_cache_valid 302 10m;\r\n    }\r\n   location ~ ^/(WEB-INF)/ {\r\n            deny all;\r\n     }\r\n```\r\n* 修改tomcat下的server配置文件并指定为默认的访问.路径**/etc/nginx/conf.d/default.conf**\r\n```\r\n<Engine name=\"Catalina\"defaultHost=\"blog.yoqu.org\"\">\r\n<Host name=\"blog.yoqu.org\" appBase=\"/web\" unpackWARs=\"true\" autoDeploy=\"true\"> \r\n<Context path=\"/\" docBase=\"webapp\" reloadable=\"true\"/>\r\n</Host>\r\n```', '2016-05-19 17:06:59', '2016-05-19 17:06:59', 'centos,tomcat,openjdk,mysql,vsftpd,nginx', '0');
INSERT INTO `node` VALUES ('30', '1', 'linux 安装php扩展记录', '>学习laravel过程中，我需要开启xdebug进行调试，然后xdebug官网看了下说明，linux系统下需要编译安装，配置php.ini\r\n官网地址：[xdebug](https://xdebug.org/download.php)\r\n###具体过程：\r\n* 下载官方源码包\r\n* 解压并进入目录进行编译\r\n* 将编译好的文件放入php的扩展中（也可以不用放）\r\n* 在php.ini文件中添加扩展\r\n\r\n##用到的一些命令\r\n**解压:**\r\n```\r\n tar -zxvf xdebug-2.4.0rc4.tgz\r\n```\r\n**编译**\r\n```\r\nphpize\r\n./configure\r\nmake\r\n```\r\n**复制文件到指定目录**\r\n```\r\ncp modules/xdebug.so /opt/lampp/lib/php/extensions/no-debug-non-zts-20151012\r\n```\r\n**编辑php.ini文件并加入新的一行\r\n```\r\nvim /opt/lampp/etc/php.ini\r\n//添加下面这一句到文件中\r\nzend_extension = /opt/lampp/lib/php/extensions/no-debug-non-zts-20151012/xdebug.so\r\n```\r\n###最后一步\r\n#Restart Service', '2016-05-19 17:07:47', '2016-05-19 17:07:47', 'linux,php,laravel', '0');
INSERT INTO `node` VALUES ('31', '1', 'mysql5.6允许远程服务器访问数据库', '>由于开发需要，有时我们需要能够远程访问链接数据库。默认mysql设置关闭了远程访问。我们可以通过修改设置来达到目的\r\n```\r\n$ cd /etc/mysql\r\n$ sudo vim my.cnf\r\n```\r\n在文件中将bind-address = 127.0.0.1注释掉（即在行首加#）\r\n**旧版本的MySQL（从一些资料上显示是5.0及其以前的版本）上使用的是skip-networking。**\r\n    创建一个mysql用户,并设置可以远程访问\r\n\r\n        grant usage on *.* to \'fred\'@\'localhost\' identified by \'fred\';//创建用户fred密码ferd\r\n        select host,user,password from mysql.user where user=\'fred\';//查看记录  \r\n        grant all privileges on *.* to fred@\'%\'identified by \'fred\';//设置可以远程访问\r\n\r\n###创建一个mysql用户,并设置可以远程访问\r\n```\r\ngrant usage on *.* to \'fred\'@\'localhost\' identified by \'fred\';//创建用户fred密码ferd\r\nselect host,user,password from mysql.user where user=\'fred\';//查看记录  \r\ngrant all privileges on *.* to fred@\'%\'identified by \'fred\';//设置可以远程访问\r\n```', '2016-05-24 14:00:05', '2016-05-24 14:00:05', 'mysql,数据库', '0');
INSERT INTO `node` VALUES ('32', '1', 'test', '请输入内容...asr', '2016-05-24 14:18:25', '2016-05-24 14:18:25', 'asd', '0');

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
INSERT INTO `user` VALUES ('1', 'admin', null, 'yoqulin', '1545', 'yoqulin@qq.com', 'E1:0A:DC:39:49:BA:59:AB:BE:56:E0:57:F2:0F:88:3E', '2016-04-18 16:51:12', '2016-05-27 15:30:49', '1', '0');
INSERT INTO `user` VALUES ('2', 'zia', null, 'yzuaq', '123123', 'yzxu@ema.com', '123456789', '2016-04-20 17:26:06', '2016-04-20 17:26:06', '1', '1');
INSERT INTO `user` VALUES ('3', 'tony', null, 'touni', '192281', 'ajzua@qq.com', 'E1:0A:DC:39:49:BA:59:AB:BE:56:E0:57:F2:0F:88:3E', '2016-05-04 16:38:26', '2016-05-09 13:38:51', '2', '0');
