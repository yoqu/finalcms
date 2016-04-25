package org.yoqu.cms.core.util;

import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import org.yoqu.cms.core.admin.config.Hook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 通过Cglib实现在方法调用前后向控制台输出两句字符串
 */
public class FinalProxy implements Interceptor {

    //要执行的hook
    private String hookName;
    //要被执行的方法.
    private Set<Class<?>> deals = null;

    //被回调的方法名字
    private String invokeMethodName = null;

    //回调的参数..
    private Object[] args = null;

    /**
     * 创建代理
     *
     * @param target
     * @param hookName
     * @return
     */
    public Object createProxy(Class target, String hookName) {
        try {
            deals = loadInvokes();
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

    /**
     * 创建代理方法，如果没有invokeMethodName，那么默认对父类的所有方法实现代理方法
     *
     * @param target
     * @param hookName
     * @param invokeMethodName
     * @return
     */
    public Object createProxy(Class target, String hookName, String invokeMethodName) {
        this.invokeMethodName = invokeMethodName;
        return createProxy(target, hookName);
    }

    public Set<Class<?>> loadInvokes() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //开始递归扫描modules包,筛选使用Hook注解的Class
        Set<Class<?>> calssList = ClassPathScanHandler.getPackageAllClasses(
                "org.yoqu.cms.core.admin.modules", true, Hook.class);
        return calssList;
    }

    private void doBefore() {
        for (Class one : deals) {
            try {
                Class[] parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    if (args[i].getClass().getSuperclass().equals(Controller.class)) {
                        parameterTypes[i] = args[i].getClass().getSuperclass();
                    }
                    else{
                        parameterTypes[i]=args[i].getClass();
                    }
                }
                Method method = one.getMethod(StrKit.firstCharToLowerCase(one.getSimpleName()) + hookName, parameterTypes);
                if (method.getParameterCount() == args.length) {
                    Object object = Enhancer.enhance(one);
                    LogKit.info("Method Hook     : " + one.getName() + " > " + StrKit.firstCharToLowerCase(one.getSimpleName()) + hookName);
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
        if (deals == null) {
            inv.invoke();
        }
        if (inv.getMethod().getName().equals(invokeMethodName)) {
            args = inv.getArgs();
            doBefore();
            Controller controller = (Controller) inv.getArg(0);
        }
        inv.invoke();
    }
}
