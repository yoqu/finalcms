package org.yoqu.cms.core.util;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.yoqu.cms.core.admin.config.Hook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 通过Cglib实现在方法调用前后向控制台输出两句字符串
 */
public class FinalProxy implements MethodInterceptor {

    //要代理的原始对象  
    private Object obj;
    //要执行的hook
    private String hookName;
    //要被执行的方法.
    private Set<Class<?>> deals = null;

    private String invokeMethodName = null;

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
        return Enhancer.create(target, this);
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

    /**
     * 回调方法:在代理实例上拦截并处理目标方法的调用，返回结果
     *
     * @param proxy       代理类
     * @param method      被代理的方法
     * @param params      该方法的参数数组
     * @param methodProxy
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        //如果没有扫描到注入的类直接执行回调函数.
        if (deals == null) {
            return methodProxy.invokeSuper(proxy, params);
        }
        // 调用之前
        // 调用目标方法，用methodProxy,
        // 而不是原始的method，以提高性能
        if (method.getName().equals(invokeMethodName)) {
            doBefore();
        }
        return methodProxy.invokeSuper(proxy, params);
    }

    private void doBefore() {
        for (Class one : deals) {
            try {
                Method method = one.getMethod(StrKit.firstCharToLowerCase(one.getSimpleName()) + hookName, null);
                Object object = com.jfinal.aop.Enhancer.enhance(one);
                LogKit.info("Method Hook     : " + StrKit.firstCharToLowerCase(one.getSimpleName()) + hookName);
                method.invoke(object);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void doAfter() {
//        for (InvokeDeal invokeDeal : deals) {
//            invokeDeal.afterDeal();
//        }
    }
}
