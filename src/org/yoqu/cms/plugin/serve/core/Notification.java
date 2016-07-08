package org.yoqu.cms.plugin.serve.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

import java.util.Map;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class Notification {

    private static final Logger log= LoggerFactory.getLogger(Notification.class);
    public static void sendBroadcast(String message){
        log.info("sendBroadcast to all session user start");
        if(SessionManager.getInstance().getSize()==0){
            log.info("empty session send broadcast.");
            return ;
        }
        for(Map.Entry<String,ClientSession> entry: SessionManager.getInstance().getSessions()){
            entry.getValue().writeMessage(message);
        }
    }

    public static void sendBroadcast(ClientSession session,String message){
        log.info("sendBroadcast to one session user..");
        session.writeMessage(message);
    }

    public static void sendMessage(String username,String message){
        ClientSession session =SessionManager.getInstance().getSession(username);
        session.writeMessage(message);
    }
}
