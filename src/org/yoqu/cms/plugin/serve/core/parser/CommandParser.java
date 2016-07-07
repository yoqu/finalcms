package org.yoqu.cms.plugin.serve.core.parser;

import com.jfinal.aop.Clear;
import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.plugin.serve.core.MessageHandler;
import org.yoqu.cms.plugin.serve.core.config.Constant;
import org.yoqu.cms.plugin.serve.core.config.Routes;
import org.yoqu.cms.plugin.serve.core.parser.exception.CommandMessageTypeException;

import java.lang.reflect.Method;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class CommandParser implements Parser {
    private static Logger log= LoggerFactory.getLogger(CommandParser.class);
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
            Method method1 = handlerClassz.getMethod(url[0], null);
            MessageHandler handler = handlerClassz.newInstance();
            handler.init(session,messageType,result);
            message=method1.invoke(handler, null);
            return message;
        }catch (NoSuchMethodException ex){
            log.error("class method not found,if no define method,Please create public void index"+ Constant.DEFAULT_METHOD+"() method");
            return "{result:\"error\",type:\"info\",log:\"method define error\"}";
        }
        catch (ClassCastException ex) {
            return "{result:\"error\",type:\"login\",log:\"message format error\"}";
        }

    }


}
