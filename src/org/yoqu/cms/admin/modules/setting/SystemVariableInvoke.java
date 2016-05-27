package org.yoqu.cms.admin.modules.setting;

import org.yoqu.cms.admin.modules.menu.MenuInvoke;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.config.Module;
import org.yoqu.cms.core.config.SystemVariable;
import org.yoqu.cms.core.model.Dictionary;
import org.yoqu.cms.core.util.FinalProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoqu on 16-5-27.
 */
public class SystemVariableInvoke {
    private volatile static SystemVariableInvoke systemHook;

    public static SystemVariableInvoke getInstance() {
        if (systemHook == null) {
            synchronized (MenuInvoke.class) {
                systemHook = (SystemVariableInvoke) new FinalProxy().createProxy(SystemVariableInvoke.class);
            }
        }
        return systemHook;
    }


    public boolean saveVariable(Map<String, String[]> result) {
        boolean flag = true;
        for (Map.Entry<String, String[]> item : result.entrySet()) {
            if (!Dictionary.dao.update(item.getKey(), item.getValue()[0])) {
                flag = false;
            }
        }
        SystemVariable.clearCache();
        return flag;
    }
}
