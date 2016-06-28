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

    private Map<String,Class<? extends MessageHandler>> routes=new HashMap<>();

    public void add(String name,Class<? extends MessageHandler> handler){
        routes.put(name,handler);
    }

    public Set<String> getKeys(){
        Set<String> keys =routes.keySet();
        return keys;
    }
}
