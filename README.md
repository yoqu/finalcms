
#finalcms
本项目基于java极速开发框架 **[JFinal](http://git.oschina.net/jfinal/jfinal)** 
>简化业务员代码量，极速的cms框架，java插件热插拔式支持系统工作,可任意定制系统的任意页面，插件只需要编译，界面安装即可使用.和系统插件完全分离

系统核心功能包括  
1. **前台主题嵌套**   
2. **页面样式定制**   
3. **后台人员权限管理**    
4. **插件管理**  
##项目功能数据库设计图 ##
![design_picure](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/CMS_Design_picture.png)

##项目简介


## 如何使用
#### 设置IDE
1. 下载源代码
2. 使用idea打开项目(项目使用idea 15.05的版本)
3. 主菜单的run选项卡设置
![选项卡设置](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/menu_editconfiruration.png)
4. 设置标红地方的参数
![runConfiguration](https://raw.githubusercontent.com/yoqu/finalcms/master/docs/pictrues/runConfiguration.png)
>启动类为 __org.yoqu.cms.core.config.StartFinalCMS__

#### 导入数据到数据库
1. 项目中docs目录包含finalcms.sql文件，将其导入你的数据库中，数据库使用的是mysql。
2. 在res目录的database_config.txt文件中配置数据库链接信息

#### 最后
* 快捷键shift + F10运行项目.
* 浏览器输入localhost即可访问.

##FAQ
>* **问**：数据库字段修改如何同步到项目中.
* **答**：运行核心util包中的ModelGenerator类重新生成（不推荐用户改动系统核心包）

*********

>* **问**：运行项目显示80端口被占用.
* **答**：1.关闭占用80端口的应用程序 2.在核心包的config包下StartFinalCMS类中修改START_PORT修改为你想要的启动端口

##关于
项目发起者: _yoqu_
