# CMS系统Hook使用
>Hook中文译为钩子，基于Aop思想，在系统各个点中部署钩子，能够起到中间作用.Hook主要用来被模块进行调用实现系统的基本功能并能拓展使用.


**************
## Hook使用说明
   1.在模块目录中把你要实习hook 的类加上@Hook注解
   2.新增一个方法，方法名称命名为[方法名][HookName](Class... ParameterTypes),例如我在UserController中加入一个PageInject_Before的Hook.
    ``` void userControllerPageInject__Before(Controller controller)```
    ***注意*** 方法的首字母应该小写.

****************

## Hook函数记录
| 函数名称 | 函数作用 |
| ------------- |:-------------:|
| ```void HookPageInject_Before(Controller controller) ```|  系统注入共用变量时调用的方法|
