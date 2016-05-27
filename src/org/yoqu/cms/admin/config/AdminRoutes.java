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
        paths.put(basePath + "user", UserController.class);
        paths.put(basePath + "dashboard", DashBoardController.class);
        paths.put(basePath + "people", PeopleController.class);
        paths.put(basePath + "role", RoleController.class);
        paths.put(basePath + "menu", MenuController.class);
        paths.put(basePath + "node", NodeController.class);
        paths.put(basePath+"setting", SettingController.class);
    }

    @Override
    public void config() {
        for (Map.Entry<String, Class> item : paths.entrySet()) {
            add(item.getKey(), item.getValue());
        }
    }
}
