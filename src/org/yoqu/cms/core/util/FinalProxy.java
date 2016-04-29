package org.yoqu.cms.core.util;

import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import org.yoqu.cms.core.aop.Hook;
import org.yoqu.cms.core.aop.Invoke;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class FinalProxy implements Interceptor {

    public static Set<Class<?>> hookClasses = null;
    //要执行的hook
    private String[] hookName;
    //回调的参数..
    private Object[] args = null;

    public static FinalProxy getInstance() {
        return new FinalProxy();
    }

    /**
     * 创建代理
     *
     * @param target
     * @param hookName
     * @return
     */
    public Object createProxy(Class target, String... hookName) {
        try {
            if (hookClasses == null)
                hookClasses = loadInvokes();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.hookName = hookName;
        return Enhancer.enhance(target, this);
    }


    public Set<Class<?>> loadInvokes() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //开始递归扫描modules包,筛选使用Hook注解的Class
        Set<Class<?>> calssList = ClassPathScanHandler.getPackageAllClasses(
                "org.yoqu.cms.core.admin.modules", true, Hook.class);
        return calssList;
    }

    private void doBefore(String currentHookName) {
        for (Class hook : hookClasses) {
            try {
                Class[] parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    if (args[i].getClass().getSuperclass().equals(Controller.class)) {
                        parameterTypes[i] = args[i].getClass().getSuperclass();
                    } else {
                        parameterTypes[i] = args[i].getClass();
                    }
                }
                Method method = hook.getMethod(StrKit.firstCharToLowerCase(hook.getSimpleName()) + currentHookName, parameterTypes);
                if (method.getParameterCount() == args.length) {
                    Object object = Enhancer.enhance(hook);
                    LogKit.info("Method Hook     : " + hook.getName() + " > " + StrKit.firstCharToLowerCase(hook.getSimpleName()) + currentHookName);
                    method.invoke(object, args);
                } else {
                    LogKit.error("Method Hook parameter not match.");
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
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
        }
        //判断该方法是否注解和hookName匹配，有注解代表它可以被回调执行方法.
        if (inv.getMethod().isAnnotationPresent(Invoke.class)) {
            Invoke invoke = inv.getMethod().getAnnotation(Invoke.class);
            for (int i=0;i<hookName.length;i++){
                if (invoke.value().equals(hookName[i])) {
                    args = inv.getArgs();
                    doBefore(hookName[i]);
                }
            }
        }
        inv.invoke();
    }
}
