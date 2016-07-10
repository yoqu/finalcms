package org.yoqu.cms.nima;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.nima.dao.SessionDao;
import org.yoqu.cms.plugin.serve.core.LoginSessionManager;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

/**
 * @author yoqu
 * @date 2016/7/7 0007
 * @description
 */
public class LoginHandler extends MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    public Object login() {
        try {
            ClientSession clientSession = new ClientSession(message.getString("username"), message.getString("password"), session);
            if (SessionDao.login(clientSession)) {
                LoginSessionManager.getInstance().addSession(clientSession);
                return writeSuccess("login");
            } else {
                return writeError("login fail");
            }
        } catch (JSONException e) {
            return writeError("invalid char.");
        }

    }

    public Object sendMessage() {
        try {
            String notify = message.getString("message");
            String username = message.getString("username");
            ClientSession clientSession = LoginSessionManager.getInstance().getSession(username);
            try {
                clientSession.writeMessage(notify);
                log.info("client send message to " + username + " message is: " + notify);
                return writeSuccess("sendMessage");
            } catch (Exception ex) {
                return writeError("message send fail,Please check problem.");
            }
        } catch (JSONException e) {
            return writeError("invalid char.");
        }


    }

    public Object getAllUserInfo() {
        return null;
    }

    public Object register() {
        try {
            ClientSession clientSession = new ClientSession(message.getString("username"), message.getString("password"), session);
            if (message.has("address")) {
                clientSession.setAddress(message.getString("address"));
            }
            if (SessionDao.isRegistered(clientSession.getUsername())) {
                return writeError("username is used.");
            }
            if (SessionDao.registerSession(clientSession)) {
                LoginSessionManager.getInstance().addSession(clientSession);
                return writeSuccess("register");
            } else {
                return writeError("register fail");
            }
        } catch (JSONException e) {
            return writeError("invalid char.");
        }
    }

    public Object logOut() {
        String username = (String) session.getAttribute("username");
        if (StringUtils.isBlank(username)) {
            return writeError("empty user name");
        }
        if (SessionDao.offline(username)) {
            try {
                return writeSuccess("logout");
            } catch (JSONException e) {
                return writeError("invalid char.");
            }
        } else {
            return writeError("log out fail");
        }
    }
}
