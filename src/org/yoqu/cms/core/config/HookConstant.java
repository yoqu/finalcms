package org.yoqu.cms.core.config;

import org.yoqu.cms.core.admin.config.InjectManager;
import org.yoqu.cms.core.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoqu on 2016/4/26 0026.
 */
public class HookConstant {
    public static InjectManager injectManager=null;
    public static User userDao=null;

    public static Map<String,Object> hooks= (Map<String, Object>) new HashMap().put("injectManager",new InjectManager());
}
