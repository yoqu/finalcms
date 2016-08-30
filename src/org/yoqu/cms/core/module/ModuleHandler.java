package org.yoqu.cms.core.module;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.handler.Handler;
import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;
import org.yoqu.cms.core.model.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yoqu
 * @date 2016/5/9 0009
 * @description 该类用户拦截所有模块的请求,将路径的url连接到指定的controller类中执行指定的方法,所有的属性都存入数据库中.
 *  在数据库中对url进行一个配置
 */
public class ModuleHandler extends Handler {

    private ModuleActionMapping actionMapping;

    public ModuleHandler() {
        actionMapping=new ModuleActionMapping();
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
            Action action=actionMapping.getAction(target);
            Controller controller= action.getControllerClass().newInstance();
            controller.setHttpServletRequest(request);
            controller.setHttpServletResponse(response);
            new Invocation(action,controller).invoke();
            Render render = RenderFactory.me().getDefaultRender(action.getViewPath());
            render.setContext(request, response, null).render();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
