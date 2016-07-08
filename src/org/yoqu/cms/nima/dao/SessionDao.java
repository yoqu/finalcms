package org.yoqu.cms.nima.dao;

import com.jfinal.plugin.activerecord.Db;
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
    public boolean registerSession(ClientSession session) {
        Client client = new Client();
        client.setUsername(session.getUsername());
        client.setPassword(session.getPassword());
        client.setAddress(session.getAddress());
        client.setIsActive(1);
        client.setIsDelete(0);
        return client.save();
    }


    public List<Client> getSessionList() {
        List<Client> clients = Client.dao.find("select * from client where is_delete=0");
        return clients;
    }

    public boolean login(ClientSession session) {
        Client client = Client.dao.findFirst("select * from client where is_delete=0 and username=? and password=?", session.getUsername(), session.getPassword());
        if (client == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 下线处理
     */
    public boolean offline(String username) {
        return changeActive(username, 0);
    }

    /**
     * 上线处理
     */
    public boolean online(String username) {
        return changeActive(username, 1);
    }

    private boolean changeActive(String username, int isActive) {
        Client client = Client.dao.findFirst("select * form client where is_delete=0 and username=?", username);
        client.setIsActive(isActive);
        return client.save();
    }


}
