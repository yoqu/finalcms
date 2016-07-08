package org.yoqu.cms.nima;

import org.json.JSONException;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.SessionManager;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

/**
 * @author yoqu
 * @date 2016/7/7 0007
 * @description
 */
public class LoginHandler extends MessageHandler {

    public Object login() {
        try {
            ClientSession clientSession = new ClientSession(message.getString("username"), message.getString("password"),session);
            if (message.has("address")) {
                clientSession.setAddress(message.getString("address"));
            }
            SessionManager.getInstance().addSession(clientSession);
            return writeSuccess("login");
        } catch (JSONException e) {
            return writeError("invalid char.");
        }
    }

    public Object sendMessage(){
        return null;
    }

    public Object getAllUserInfo(){

        return null;
    }



}
