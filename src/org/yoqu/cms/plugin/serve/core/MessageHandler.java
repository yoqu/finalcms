package org.yoqu.cms.plugin.serve.core;

import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.plugin.serve.core.config.ClientSession;

/**
 * @author yoqu
 * @date 2016/6/23 0023
 * @description
 */
public class MessageHandler {

    private IoSession session;//current session.

    public Object loginHandler(JSONObject message){
        ClientSession clientSession =new ClientSession();
        clientSession.setSession(session);
        try {
            clientSession.setUsername(message.getString("username"));
            clientSession.setPassword(message.getString("password"));
            if(message.has("address")){
                clientSession.setAddress(message.getString("address"));
            }
            int newSize=SessionManager.getInstance().getSize()+1;
            clientSession.setId((long)newSize);
            clientSession.setSession(session);
            session.setAttribute("clientSession",clientSession);
            session.setAttribute("id",newSize);
            SessionManager.getInstance().addSession(clientSession);
            return writeSuccess();
        } catch (JSONException e) {
            return writeError("invalid char.");
        }
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public String writeSuccess() throws JSONException {
        JSONObject result= new JSONObject();
        result.put("result","suceess");
        result.put("type","login");
        return result.toString();
    }

    public String writeError(String information){
//        JSONObject result= new JSONObject();
//        result.put("result","error");
//        result.put("type","login");
//        result.put("log",information);
        String result="{result:\"error\",type:\"login\",log:\""+information+"\"}";
        return result.toString();
    }
}
