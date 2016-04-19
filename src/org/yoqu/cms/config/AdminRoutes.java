package org.yoqu.cms.config;

import com.jfinal.config.Routes;
import org.yoqu.cms.admincontroller.DashBoardController;
import org.yoqu.cms.admincontroller.HelloController;
import org.yoqu.cms.admincontroller.UserController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yoqu on 2016/4/19 0019.
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
    }

    public AdminRoutes() {
        basePath = "/";
        initPaths();
    }

    @Override
    public void config() {
        for (Map.Entry<String,Class> item : paths.entrySet()) {
            add(item.getKey(),item.getValue());
        }
    }
}
