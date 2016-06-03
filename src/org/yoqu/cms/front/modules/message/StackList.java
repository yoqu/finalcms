package org.yoqu.cms.front.modules.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author yoqu
 * @date 2016/6/2 0002
 * @description
 */
public class StackList {


    private static Stack<Message> messages= new Stack<>();

    public static void push(Message message){
        messages.push(message);
    }

    public static Message pop(){

        return messages.pop();
    }

    /**
     *   有消息返回真
     */
    public static boolean isNotify(){
        return !messages.isEmpty();
    }
}
