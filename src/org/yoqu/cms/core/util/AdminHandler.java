package org.yoqu.cms.core.util;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import org.yoqu.cms.core.config.FinalCms;
import org.yoqu.cms.core.model.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/9 0009
 * @description
 */
public class AdminHandler extends Handler {

    private class ActionMapping {
        private List<Url> urls;

        public ActionMapping(){
            buildMapping();
        }
        public void buildMapping() {
            urls = Url.dao.find("select * from url where is_delete=0");
        }

        public Url get(String target) {
            for (Url url : urls) {
                if (FileNameMatcher.match(target,url.getUrl())){
                    return url;
                }
            }
            return null;
        }
    }

    private ActionMapping actionMapping;
    public AdminHandler() {
        actionMapping=new ActionMapping();
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.indexOf('.') != -1) {
            return ;
        }
        Url url=actionMapping.get(target);
        if (url==null){
            next.handle(target, request, response, isHandled);
            return;
        }
        try {
            String[] urlPara={""};

            Action action=JFinal.me().getAction(url.getUrl(),urlPara);
            List<Interceptor> interceptors= FinalCms.getInstance().getInterceptors();

            Controller controller= (Controller) Class.forName(url.getController()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public Action getAction(String target) {
//        Action action = JFinal.me().getAction(target, urlpara);
//        if (action != null) {
//            return action;
//        }
//        int lastIndex = target.lastIndexOf("/");
//        String toUrl = target.substring(0, lastIndex);
//
//        String[] urlpara = {null};
//        if (target.indexOf("/") != -1) {
//
//        }
//    }
}
