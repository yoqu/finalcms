package org.yoqu.cms.core.admin.modules.menu;

import com.jfinal.core.Controller;
import org.yoqu.cms.core.admin.config.Hook;

/**
 * Created by yoqu on 2016/4/25 0025.
 */
@Hook
public class InitMenu {

    public void initMenuPageInject_Before(Controller controller){
        controller.setAttr("menu","app");
        System.out.println("执行了InitMenu下的Hook方法");
    }
}
