package org.yoqu.cms.plugin.serve;

import org.yoqu.cms.nima.LoginHandler;
import org.yoqu.cms.plugin.serve.core.config.Constant;
import org.yoqu.cms.plugin.serve.core.config.Routes;
import org.yoqu.cms.plugin.serve.core.config.ServeConfig;

/**
 * @author yoqu
 * @date 2016/6/28 0028
 * @description
 */
public class MyserveConfig extends ServeConfig {

    @Override
    public void configRoute(Routes routes) {
        routes.add("login", LoginHandler.class);
    }

    @Override
    public void configConstant(Constant constant) {

    }

    @Override
    public Routes getRoutes() {
        return null;
    }

    @Override
    public Constant getConstant() {
        return null;
    }
}
