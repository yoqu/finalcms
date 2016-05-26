package org.yoqu.cms.core.util;

import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import org.yoqu.cms.core.aop.Hook;
import org.yoqu.cms.core.aop.InvokeAfter;
import org.yoqu.cms.core.aop.InvokeBefore;
import org.yoqu.cms.core.config.FinalBaseController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class FinalProxy implements Interceptor {

    public static Set<Class<?>> hookClasses = null;
    //回调的参数..
    private Object[] args = null;

    private Object returnValue = null;


    /**
     * 创建代理
     *
     * @param target
     * @return
     */
    public Object createProxy(Class target) {
        try {
            if (FinalProxy.hookClasses == null)
                FinalProxy.hookClasses = loadInvokes();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Enhancer.enhance(target, this);
    }


    public Set<Class<?>> loadInvokes() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //开始递归扫描modules包,筛选使用Hook注解的Class
        Set<Class<?>> calssList = ClassPathScanHandler.getPackageAllClasses(
                "org.yoqu.cms.admin.modules", true, Hook.class);
        return calssList;
    }

    private void doHookThing(String currentHookName) {
        for (Class hook : hookClasses) {
            try {
                //args不为空说明是Before执行
                if (args != null && returnValue == null) {
                    Class[] parameterTypes = new Class[args.length];
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].getClass().getSuperclass().equals(FinalBaseController.class)) {
                            parameterTypes[i] = args[i].getClass().getSuperclass();
                        } else {
                            parameterTypes[i] = args[i].getClass();
                        }
                    }
                    try {
                        Method method = hook.getMethod(StrKit.firstCharToLowerCase(hook.getSimpleName()) + currentHookName, parameterTypes);
                        if (method.getParameterCount() == args.length) {
                            Object object = Enhancer.enhance(hook);
                            LogKit.info("Before Method Hook     : " + hook.getName() + " > " + StrKit.firstCharToLowerCase(hook.getSimpleName()) + currentHookName);
                            method.invoke(object, args);
                        } else {
                            LogKit.error("Method Hook parameter not match.");
                        }
                    } catch (NoSuchMethodException e) {
                        LogKit.error("NoSuchMethodException:  " + e.getMessage());
                        return;
                    }
                }
                //after执行
                if (returnValue != null) {
                    try {
                        Method method = hook.getMethod(StrKit.firstCharToLowerCase(hook.getSimpleName()) + currentHookName, returnValue.getClass());
                        Object object = Enhancer.enhance(hook);
                        LogKit.info("After Method Hook     : " + hook.getName() + " > " + StrKit.firstCharToLowerCase(hook.getSimpleName()) + currentHookName);
                        method.invoke(object, returnValue);
                    } catch (NoSuchMethodException e) {
                        LogKit.error("NoSuchMethodException:  " + e.getMessage());
                        return;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void intercept(Invocation inv) {
        //如果没有扫描到注入的类直接执行回调函数.
        if (hookClasses == null) {
            inv.invoke();
            return;
        }
        //判断该方法是否注解和hookName匹配，有注解代表它可以被回调执行方法.
        if (inv.getMethod().isAnnotationPresent(InvokeBefore.class)) {
            InvokeBefore invoke = inv.getMethod().getAnnotation(InvokeBefore.class);
            args = inv.getArgs();
            doHookThing(invoke.value());
        }
        inv.invoke();
        returnValue = inv.getReturnValue();

        if (inv.getMethod().isAnnotationPresent(InvokeAfter.class)) {
            InvokeAfter invokeAfter = inv.getMethod().getAnnotation(InvokeAfter.class);
            doHookThing(invokeAfter.value());
        }
    }
}
