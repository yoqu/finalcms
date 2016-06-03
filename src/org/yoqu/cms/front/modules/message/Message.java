package org.yoqu.cms.front.modules.message;

/**
 * @author yoqu
 * @date 2016/6/2 0002
 * @description
 */
public class Message {
    public Message(String message,String status){
        this.message=message;
        this.status=status;
    }

    private String message;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
