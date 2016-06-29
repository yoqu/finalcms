package org.yoqu.cms.plugin.serve.core.config;

import org.yoqu.cms.plugin.serve.core.MessageHandler;

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
        routes.put(name, handler);
    }

    public Set<String> getKeys() {
        Set<String> keys = routes.keySet();
        return keys;
    }

    public Class<? extends MessageHandler> get(String key,String[] url) {
        Class route = routes.get(key);
        if (route != null) {
            return route;
        }
        int i = key.lastIndexOf(SLASH);
        if (i != -1) {
            route = routes.get(key.substring(0, i));
            url[0]=key.substring(i,key.length());
        }
        return route;
    }
}
