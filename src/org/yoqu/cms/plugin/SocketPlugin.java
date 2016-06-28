package org.yoqu.cms.plugin;

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

    private static final Log log=Log.getLog(SocketPlugin.class);
//    public static void main(String[] args){
//        SocketService socketService=new SocketService();
//        socketService.start();
//    }
    public SocketService socketService;
    private Constant constant=new Constant();
    private Routes routes=new Routes();
    @Override
    public boolean start() {
        return socketService.start();
    }

    public void initService(){
        socketService=new SocketService();
        ServeConfig config=new MyserveConfig();
        config.configConstant(constant);
        config.configRoute(routes);
    }

    @Override
    public boolean stop() {
        log.info("stop socket plugin..");
        return socketService.stop();
    }
}
