package org.yoqu.cms.plugin.serve.core.parser;

import com.jfinal.aop.Clear;
import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.config.Routes;
import org.yoqu.cms.plugin.serve.core.parser.exception.CommandMessageTypeException;

import java.lang.reflect.Method;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class CommandParser implements Parser {
    private Routes routes;

    public CommandParser(Routes handler) {
        this.routes = handler;
    }


    private JSONObject castMessage(Object message) throws CommandMessageTypeException, ClassCastException, JSONException {
        message = message.toString().replace("\r", "");
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
            if (result.isNull("type")) {
                throw new CommandMessageTypeException("must define json key[type]");
            }
            String messageType = result.getString("type");
            String[] url = new String[]{null};
            Class<? extends MessageHandler> handlerClassz = routes.get(messageType, url);

//            if (handlerClassz.isAnnotationPresent(Clear.class))
            Method method1 = handlerClassz.getMethod(url[0], null);
            MessageHandler handler = handlerClassz.newInstance();
            handler.init(session);
            method1.invoke(handler, null);

//            if (!isAuth(session)) {
//                if (!messageType.equals(TYPE_LOGIN))
//                    return handler.writeError("user not auth.");
//            }
//
//            switch (messageType) {
//                case TYPE_LOGIN:
//                    message = handler.loginHandler(result);
//                    break;
//            }
            return message;
        } catch (ClassCastException ex) {
            return "{result:\"error\",type:\"login\",log:\"message format error\"}";
        }

    }


}
