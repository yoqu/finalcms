package org.yoqu.cms.plugin;

import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import org.yoqu.cms.plugin.serve.SocketService;

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

    @Override
    public boolean start() {
        System.out.println("start socket..");
        log.info("start socket plugin..");
        return SocketService.getInstance().start();
    }

    @Override
    public boolean stop() {
        log.info("stop socket plugin..");
        return SocketService.getInstance().stop();
    }
}
