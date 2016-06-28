package org.yoqu.cms.plugin.serve.core;

import org.yoqu.cms.plugin.serve.core.config.ClientSession;

import java.util.LinkedList;

/**
 * @author yoqu
 * @date 2016/6/21 0021
 * @description
 */
public class SessionManager {
    private static SessionManager sessionManager;

    public static SessionManager getInstance(){
        if(sessionManager==null){
            synchronized (SessionManager.class){
                sessionManager=new SessionManager();
            }
        }
        return sessionManager;
    }

    private LinkedList<ClientSession> sessions=new LinkedList<>();

    public void addSession(ClientSession clientSession){
        sessions.add(clientSession);
    }

    public LinkedList<ClientSession> getSessions(){
        return this.sessions;
    }

    public void removeSession(ClientSession clientSession){
        sessions.remove(clientSession);
    }

    public  void removeSession(int id){
        sessions.remove(id);
    }

    /**
     * 获取当前session的数量
     * @return
     */
    public int getSize(){
        return sessions.size();
    }
}
