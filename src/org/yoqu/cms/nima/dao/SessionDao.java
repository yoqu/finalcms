package org.yoqu.cms.nima.dao;

import org.yoqu.cms.core.model.Client;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/7/8 0008
 * @description
 */
public class SessionDao {

    /**
     * 注册Session
     *
     * @param session
     */
    public static boolean registerSession(ClientSession session) {
        Client client = new Client();
        client.setUsername(session.getUsername());
        client.setPassword(session.getPassword());
        client.setAddress(session.getAddress());
        client.setIsActive(1);
        client.setIsDelete(0);
        return client.save();
    }

    public static boolean isRegistered(String username) {
        return Client.dao.findFirst("select * from client where username=? and is_delete=0", username) != null ? true : false;
    }

    public static List<Client> getSessionList() {
        List<Client> clients = Client.dao.find("select * from client where is_delete=0");
        return clients;
    }

    public static boolean login(ClientSession session) {
        Client client = Client.dao.findFirst("select * from client where is_delete=0 and username=? and password=?", session.getUsername(), session.getPassword());
        if (client == null) {
            return false;
        } else {
            client.setIsActive(1);
            client.update();
            return true;
        }
    }

    /**
     * 下线处理
     */
    public static boolean offline(String username) {
        return changeActive(username, 0);
    }

    /**
     * 上线处理
     */
    public static boolean online(String username) {
        return changeActive(username, 1);
    }

    private static boolean changeActive(String username, int isActive) {
        Client client = Client.dao.findFirst("select * form client where is_delete=0 and username=?", username);
        client.setIsActive(isActive);
        return client.save();
    }


}
