package org.yoqu.cms.plugin.serve.core.parser;

import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.parser.exception.CommandMessageTypeException;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class CommandParser implements Parser {

    private final String TYPE_LOGIN = "login";
    private MessageHandler handler;

    public CommandParser(MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean isWrite(Object message) throws CommandMessageTypeException {
        JSONObject result = null;
        try {
            result = castMessage(message);
        } catch (JSONException e) {

            e.printStackTrace();
        }
        if (result.isNull("type")) {
            throw new CommandMessageTypeException("must define json key[result,type]");
        }
        try {
            if (result.getString("type").equals(TYPE_LOGIN)) {
                return false;
            } else {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    private JSONObject castMessage(Object message) throws CommandMessageTypeException, ClassCastException, JSONException {
        message =message.toString().replace("\r","");
        JSONObject result = new JSONObject(message.toString());
        if (result == null) {
            throw new CommandMessageTypeException();
        }
        return result;
    }

    @Override
    public boolean isAuthMessage(Object message) {
        return false;
    }

    private boolean isAuth(IoSession session) {
        if (session.getAttribute("client") != null) {
            return true;
        } else {
            return false;
        }
    }

    public Object parserMessage(Object message, IoSession session) throws CommandMessageTypeException, Exception {
        try {
            JSONObject result = castMessage(message);
            handler.setSession(session);
            if (result.isNull("type")) {
                throw new CommandMessageTypeException("must define json key[type]");
            }
            String messageType = result.getString("type");
            if (!isAuth(session)) {
                if (!messageType.equals(TYPE_LOGIN))
                    return handler.writeError("user not auth.");
            }
            switch (messageType) {
                case TYPE_LOGIN:
                    message = handler.loginHandler(result);
                    break;
            }
            return message;
        }catch (ClassCastException ex){
            return handler.writeError("message format error");
        }

    }


}
