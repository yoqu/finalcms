package org.yoqu.cms.admin.config;

import org.yoqu.cms.admin.modules.dashboard.DashBoardController;
import org.yoqu.cms.admin.modules.menu.MenuController;
import org.yoqu.cms.admin.modules.node.NodeController;
import org.yoqu.cms.admin.modules.people.PeopleController;
import org.yoqu.cms.admin.modules.role.RoleController;
import org.yoqu.cms.admin.modules.setting.SettingController;
import org.yoqu.cms.admin.modules.user.UserController;
import com.jfinal.config.Routes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoqu on 2016/4/19 .
 */
public class AdminRoutes extends Routes {
    public static Map<String, Class> paths = new HashMap<>();
    private String basePath;

    public AdminRoutes(String basePath) {
        this.basePath = "/" + basePath + "/";
        initPaths();
    }

    public AdminRoutes() {
        basePath = "/";
        initPaths();
    }

    public void initPaths() {
        paths.put(getUrlModel("user"), UserController.class);
        paths.put(getUrlModel("dashboard"), DashBoardController.class);
        paths.put(getUrlModel("people"), PeopleController.class);
        paths.put(getUrlModel("role"), RoleController.class);
        paths.put(getUrlModel("menu"), MenuController.class);
        paths.put(getUrlModel("node"), NodeController.class);
        paths.put(getUrlModel("setting"), SettingController.class);
    }

    public String getUrlModel(String model){
        return basePath + model;
    }
    @Override
    public void config() {
        for (Map.Entry<String, Class> item : paths.entrySet()) {
            add(item.getKey(), item.getValue());
        }
    }
}
