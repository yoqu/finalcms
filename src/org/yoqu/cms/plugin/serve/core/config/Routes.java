package org.yoqu.cms.plugin.serve.core.config;

import com.jfinal.core.Controller;
import org.yoqu.cms.plugin.serve.core.MessageHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yoqu
 * @date 2016/6/28 0028
 * @description
 */
public class Routes {
    private static final String SLASH = "/";

    private Map<String, Class<? extends MessageHandler>> routes = new HashMap<>();

    public void add(String name, Class<? extends MessageHandler> handler) {
//        boolean sonOfMessageHandler = (handler.getSuperclass() == MessageHandler.class);
//        Method[] methods = (sonOfMessageHandler ? handler.getDeclaredMethods() : handler.getMethods());
//        for (Method method:methods) {
//            String methodName = method.getName();
//            if (method.getParameterTypes().length != 0)
//                continue ;
//            if (sonOfMessageHandler && !Modifier.isPublic(method.getModifiers()))
//                continue ;
//            if(methodName.equals(Constant.DEFAULT_METHOD)){
//                routes.put(name,handler);
//            }else{
//                routes.put(name+"/"+methodName,handler);
//            }
//        }
        routes.put(name,handler);
    }

    public Set<String> getKeys() {
        Set<String> keys = routes.keySet();
        return keys;
    }

    public Class<? extends MessageHandler> get(String key,String[] url) {
        Class route = routes.get(key);
        if (route != null) {
            url[0]=Constant.DEFAULT_METHOD;
            return route;
        }
        int i = key.lastIndexOf(SLASH);
        if (i != -1) {
            route = routes.get(key.substring(0, i));
            url[0]=key.substring(i+1,key.length());
        }
        return route;
    }


}
