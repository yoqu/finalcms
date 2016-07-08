package org.yoqu.cms.plugin.serve;

import com.jfinal.kit.PropKit;
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
        routes.add("user", LoginHandler.class);
    }

    @Override
    public void configConstant(Constant constant) {
        constant.port=PropKit.use("socket_config.txt").getInt("port");
        constant.hostname=PropKit.use("socket_config.txt").get("hostname");
    }

}
