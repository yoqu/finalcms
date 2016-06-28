package org.yoqu.cms.plugin.serve;

import java.util.Collections;
import java.util.Comparator;
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

    public  void removeSession(int index){
        sessions.remove(index);
    }

    public ClientSession getSessionById(Long id){
        for (ClientSession session:sessions){
            if(session.getId()==id){
                return session;
            }
        }
        return null;
    }
}
