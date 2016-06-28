package org.yoqu.cms.plugin.serve.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class Notification {

    private static final Logger log= LoggerFactory.getLogger(Notification.class);
    public static void sendBroadcast(String message){
        log.info("sendBroadcast to all session user start");
        if(SessionManager.getInstance().getSessions().size()==0){
            log.info("empty session send broadcast.");
            return ;
        }
        for(ClientSession clientSession: SessionManager.getInstance().getSessions()){
            clientSession.getSession().write(message);
        }
    }

    public static void sendBroadcast(ClientSession session,String message){
        log.info("sendBroadcast to one session user..");
        session.getSession().write(message);
    }
}
