package org.yoqu.cms.plugin;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import org.yoqu.cms.plugin.serve.MyserveConfig;
import org.yoqu.cms.plugin.serve.SocketService;
import org.yoqu.cms.plugin.serve.core.config.Constant;
import org.yoqu.cms.plugin.serve.core.config.Routes;
import org.yoqu.cms.plugin.serve.core.config.ServeConfig;

/**
 * @author yoqu
 * @date 2016/6/21 0021
 * @description
 */
public class SocketPlugin implements IPlugin {

    private static final Log log = Log.getLog(SocketPlugin.class);
    public SocketService socketService;
    private Constant constant = new Constant();
    private Routes routes = new Routes();

    @Override
    public boolean start() {
        if (!initService())
            return false;
        return socketService.start();
    }

    public boolean initService() {
        String configName = PropKit.use("socket_config.txt").get("config");
        try {
            Class classz = Class.forName(configName);
            Object temp = classz.newInstance();
            if (temp instanceof ServeConfig != true) {
                log.error("config class must extends ServeConfig,Please confirm.");
                return false;
            }
            ServeConfig config = (ServeConfig) temp;
            config.configConstant(constant);
            config.configRoute(routes);
            socketService = new SocketService(constant,routes);
            return true;
        } catch (ClassNotFoundException e) {
            log.error("config class not define or socket_config.txt exclude config property");
            return false;
        } catch (InstantiationException e) {
            log.error("constructor method exception.Please check your constructor method.");
            return false;
        } catch (IllegalAccessException e) {
            log.error("method,field access exception,Please checked.");
            return false;
        }
    }

    @Override
    public boolean stop() {
        log.info("stop socket plugin..");
        return socketService.stop();
    }
}
