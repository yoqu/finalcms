package org.yoqu.cms.plugin.serve.core.parser.exception;

/**
 * @author yoqu
 * @date 2016/6/22 0022
 * @description
 */
public class MessageTypeException extends Exception {

    public MessageTypeException() {
        this("Message Type not match.");
    }

    public MessageTypeException(String message){
        super(message);
    }
}
