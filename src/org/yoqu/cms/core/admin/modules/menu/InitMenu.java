package org.yoqu.cms.core.admin.modules.menu;

import com.jfinal.core.Controller;
import org.yoqu.cms.core.aop.Hook;
import org.yoqu.cms.core.model.User;

import java.util.ArrayList;

/**
 * Created by yoqu on 2016/4/25 0025.
 */
@Hook
public class InitMenu {


    public void initMenuPageInject_Before(Controller controller){
        controller.setAttr("menu","app");
        System.out.println("执行了InitMenu下的Hook方法");
    }


    public void initMenuFindUser_Before(String... parameters){
        System.out.println("执行hookUser_before 方法");
    }


    public void initMenuFindUser_After(ArrayList<User> users){
        System.out.println("FindUser_After--users:"+users);
    }
}
