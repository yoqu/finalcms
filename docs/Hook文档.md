# Final CMS系统Hook使用及定义
>Hook中文译为钩子，基于Aop思想，在系统各个点中部署钩子，能够起到中间作用.Hook主要用来被模块进行调用实现系统的基本功能并能拓展使用.


**************
## Hook定义

Hook分为``` @InvokeBefore("hookMethodName") @InvokeAfter("hookMethodName")```
InvokeBefore用于hook执行主体方法前插入方法执行代码
InvokeAfter用于hook执行主体方法后插入方法执行代码
Invoke注解用于方法体上，例如User模块下的Userinvoke类中的某一个方法
```
    @InvokeBefore("FindUser_Before")
    @InvokeAfter("FindUser_After")
    public List<User> finduserByNamePasswordOrName(String... parameters) {
        if (parameters.length == 2) {
            return User.dao.find("select * from user where name=? and password=? and is_delete=0", parameters);
        } else if (parameters.length == 1) {
            return User.dao.find("select * from user where name=? and is_delete=0", parameters);
        } else if (parameters.length == 0) {
            return User.dao.find("select * from user where is_delete=0");
        } else return null;
    }
```

## Hook使用说明
   1.在模块目录中把你要实现hook 的类加上@Hook注解
   2.新增一个方法，方法名称命名为[className][HookName](Class... ParameterTypes),例如我在UserController中加入一个PageInject_Before的Hook.
    ``` void userControllerPageInject__Before(Controller controller)```
    ***注意*** 方法的首字母应该小写.

****************

## Hook函数记录
| 函数名称 | 函数作用 |
| ------------- |:-------------:|
| ```void HookPageInject_Before(Controller controller) ```|  系统注入共用变量时调用的方法|
| ```void HookSaveUser_Before(User user) ```|  系统执行保存用户操作（包括添加和更新）|
| ```void HookPageInject_Before(Controller controller) ```|  系统注入共用变量时调用的方法|

## 注意事项

- 在类中使用Hook不能使用父类的方法，因为调用的URl返回的结果并不一样.
