package org.yoqu.cms.plugin.serve.parser.exception;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class CommandMessageTypeException extends MessageTypeException {

    public CommandMessageTypeException() {
        super("Command message type not match.");
    }

    public CommandMessageTypeException(String message){
        super(message);
    }
}
