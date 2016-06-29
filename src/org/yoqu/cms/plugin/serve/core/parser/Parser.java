package org.yoqu.cms.plugin.serve.core.parser;

import org.apache.mina.core.session.IoSession;
import org.yoqu.cms.plugin.serve.core.parser.exception.CommandMessageTypeException;
import org.yoqu.cms.plugin.serve.core.parser.exception.MessageTypeException;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public interface Parser {

    boolean isAuthMessage(Object message);

    Object parserMessage(Object message, IoSession session) throws CommandMessageTypeException, Exception;
}
