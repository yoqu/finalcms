package org.yoqu.cms.nima;

import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.SessionManager;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

/**
 * @author yoqu
 * @date 2016/7/7 0007
 * @description
 */
public class LoginHandler extends MessageHandler {

    public Object index(){
        ClientSession clientSession = new ClientSession();
        clientSession.setSession(session);
        try {
            clientSession.setUsername(message.getString("username"));
            clientSession.setPassword(message.getString("password"));
            if (message.has("address")) {
                clientSession.setAddress(message.getString("address"));
            }
            clientSession.setSession(session);
            SessionManager.getInstance().addSession(clientSession);
            return writeSuccess("login");
        } catch (JSONException e) {
            return writeError("invalid char.");
        }
    }
}
