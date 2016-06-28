package org.yoqu.cms.plugin.serve.parser;

import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;
import org.yoqu.cms.plugin.serve.parser.exception.CommandMessageTypeException;
import org.yoqu.cms.plugin.serve.parser.exception.MessageTypeException;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public interface Parser {
    /**
     * 判断消息是否需要传输至客户端
     * @param message 消息内容
     * @return
     */
    boolean isWrite(Object message) throws MessageTypeException;

    boolean isAuthMessage(Object message);

    Object parserMessage(Object message, IoSession session) throws CommandMessageTypeException, Exception;
}
