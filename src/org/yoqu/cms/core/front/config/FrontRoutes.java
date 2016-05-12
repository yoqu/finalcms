package org.yoqu.cms.core.front.config;

import com.jfinal.config.Routes;
import org.yoqu.cms.core.front.modules.index.IndexController;
import org.yoqu.cms.core.front.modules.node.NodeController;

/**
 * @author yoqu
 * @date 2016/5/12 0012
 * @description
 */
public class FrontRoutes extends Routes {


    @Override
    public void config() {
        add("/", IndexController.class);
        add("node", NodeController.class);
    }
}
