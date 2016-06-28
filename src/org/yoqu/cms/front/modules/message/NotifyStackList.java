package org.yoqu.cms.front.modules.message;

import java.util.Stack;

/**
 * @author yoqu
 * @date 2016/6/2 0002
 * @description
 */
public class NotifyStackList {

    private static Stack<Notify> notifies= new Stack<>();

    public static void push(Notify notify){
        notifies.push(notify);
    }

    public static Notify pop(){
        return notifies.pop();
    }

    /**
     *   有消息返回真
     */
    public static boolean isNotify(){
        return !notifies.isEmpty();
    }

    class Notify {

        private String content;
        private String title;
        private String display;
        private String status;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}

