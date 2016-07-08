package org.yoqu.cms.plugin.serve.core;

import org.yoqu.cms.plugin.serve.core.config.ClientSession;

import java.util.*;

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

    private HashMap<String,ClientSession> sessions=new HashMap<>();

    private HashSet<ClientSession> clientSessions=new HashSet<>();

    public void addSession(ClientSession clientSession){
        clientSession.setId((long) (getSize()+1));
        clientSession.getSession().setAttribute("id",getSize()+1);
        clientSession.getSession().setAttribute("clientSession", clientSession);
        sessions.put(clientSession.getUsername(),clientSession);
    }


    public ClientSession getSession(String username){
        return sessions.get(username);
    }
    public void removeSession(ClientSession clientSession){
        sessions.remove(clientSession);
    }

    public  void removeSession(String username){
        sessions.remove(username);
    }

    /**
     * 获取当前session的数量
     * @return
     */
    public int getSize(){
        return sessions.size();
    }

    public Set<Map.Entry<String,ClientSession>> getSessions(){
        return sessions.entrySet();
    }
}
