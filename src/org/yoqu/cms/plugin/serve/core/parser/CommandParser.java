package org.yoqu.cms.plugin.serve.core.parser;

import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.config.Constant;
import org.yoqu.cms.plugin.serve.core.config.Routes;
import org.yoqu.cms.plugin.serve.core.parser.exception.CommandMessageTypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class CommandParser implements Parser {
    private static Logger log = LoggerFactory.getLogger(CommandParser.class);
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

    public Object parserMessage(Object message, IoSession session) throws CommandMessageTypeException {
        try {
            JSONObject result = castMessage(message);
            if (result.isNull("type")) {
                throw new CommandMessageTypeException("must define json key[type]");
            }
            String messageType = result.getString("type");
            String[] url = new String[]{"index"};
            Class<? extends MessageHandler> handlerClassz = routes.get(messageType, url);
            message = messageInvoke(handlerClassz, url[0], messageType, result, session);
            return message;
        } catch (JSONException ex) {
            return printErrorMessage("message type must json","log");
        } catch (ClassCastException ex) {
            return "{result:\"error\",type:\"login\",log:\"message format error\"}";
        }

    }

    @SuppressWarnings("NullArgumentToVariableArgMethod")
    public Object messageInvoke(Class<? extends MessageHandler> handlerClassz, String url, String messageType, JSONObject result, IoSession session) {
        Method method1 = null;
        try {
            method1 = handlerClassz.getMethod(url, null);
            MessageHandler handler = handlerClassz.newInstance();
            handler.init(session, messageType, result);
            return method1.invoke(handler, null);
        } catch (NoSuchMethodException e) {
            log.error("class method not found,if no define method,Please create public void index" + Constant.DEFAULT_METHOD + "() method");
            return printErrorMessage("method define error", messageType);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return printErrorMessage(e.getMessage(), messageType);
        } catch (InstantiationException e) {
            log.error(e.getMessage());
            return printErrorMessage(e.getMessage(), messageType);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage());
            return printErrorMessage(e.getMessage(), messageType);
        }
    }

    public String printErrorMessage(String message, String type) {
        return "{result:\"error\",type:\"" + type + "\",log:\"" + message + "\"}";
    }

}
