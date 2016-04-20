package org.yoqu.cms.core.config;

import com.jfinal.config.Routes;
import org.yoqu.cms.core.admincontroller.DashBoardController;
import org.yoqu.cms.core.admincontroller.People;
import org.yoqu.cms.core.admincontroller.UserController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoqu on 2016/4/19 .
 */
public class AdminRoutes extends Routes {
    private String basePath;
    public static Map<String, Class> paths = new HashMap<>();

    public AdminRoutes(String basePath) {
        this.basePath = "/" + basePath + "/";
        initPaths();
    }

    public void initPaths() {
        paths.put(basePath + "user", UserController.class);
        paths.put(basePath + "dashboard", DashBoardController.class);
        paths.put(basePath+"people", People.class);
    }

    public AdminRoutes() {
        basePath = "/";
        initPaths();
    }

    @Override
    public void config() {
        for (Map.Entry<String, Class> item : paths.entrySet()) {
            add(item.getKey(), item.getValue());
        }
    }
}
