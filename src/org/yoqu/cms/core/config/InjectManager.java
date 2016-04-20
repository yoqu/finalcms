package org.yoqu.cms.core.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.CacheKit;
import org.yoqu.cms.core.Constant.Constant;
import org.yoqu.cms.core.Constant.SystemVariable;
import org.yoqu.cms.core.util.SiteTitle;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by yoqu on 2016/4/4 .
 */
public class InjectManager {
    private static final Log log = Log.getLog(InjectManager.class);
    public static volatile boolean firstLoad = true;//判断是否第一次加载，如果第一次加载调用init方法初始化参数

    public static synchronized void init() {
        SystemVariable.use();//初始化参数
    }


    public static void injectCommonVariable(Controller controller) {
        injectSystemVariable(controller);//inject System Variable..
        injectOtherVariable(controller);
    }

    public static void injectPersonalVariable(Controller controller){
        injectOnlineUser(controller);//inject system Online User variable..
    }

    private static void injectOtherVariable(Controller controller) {
        //inject site url.
        StringBuffer url = controller.getRequest().getRequestURL();
        String tempContextUrl = url.delete(url.length() - controller.getRequest().getRequestURI().length(), url.length()).append(controller.getRequest().getServletContext().getContextPath()).append("/").toString();
        controller.setAttr(Constant.SITE_URL, tempContextUrl);
    }

    /**
     * inject system variable.
     *
     * @param controller
     */
    private static void injectSystemVariable(Controller controller) {
        if (firstLoad) {
            init();
            firstLoad = false;
        }
        for (HashMap.Entry<String, String> item : SystemVariable.systemConstant.entrySet()) {
            controller.setAttr(item.getKey().trim(), item.getValue().trim());
        }
    }

    private static void injectOnlineUser(Controller controller) {
        controller.setAttr(Constant.ONLINE_USER, controller.getSessionAttr(Constant.ONLINE_USER));
    }

    public static void injectAnnotation(Method method, Controller controller) {
        if (method.isAnnotationPresent(SiteTitle.class)) {
            SiteTitle siteTitle = method.getAnnotation(SiteTitle.class);
            controller.setAttr(Constant.SITE_TITLE, siteTitle.value());
        }
    }

}
