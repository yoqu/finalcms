package org.yoqu.cms.plugin.serve;

import org.apache.mina.core.session.IoSession;

/**
 * @author yoqu
 * @date 2016/6/21 0021
 * @description
 */
public class ClientSession {
    private Long id;
    private IoSession session;
    private String address;
    private String username;
    private String password;
    private boolean isActive;


    public IoSession getSession() {
        return session;
    }
    public void setSession(IoSession session) {
        this.session = session;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
