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
public abstract class MessageHandler {
    protected JSONObject message;
    protected IoSession session;//current session.
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void init(IoSession session, String type, JSONObject message) {
        this.session = session;
        this.type = type;
        this.message = message;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public String writeSuccess(String type) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", "success");
        result.put("type", type);
        return result.toString();
    }

    public String writeError(String information) {
       return writeError(information,type);
    }

    public String writeError(String information,String messageType){
        return "{result:\"error\",type:\""+messageType+"\",log:\"" + information + "\"}";
    }
}
