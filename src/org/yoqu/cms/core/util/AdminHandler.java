package org.yoqu.cms.core.util;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.log.Log;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author yoqu
 * @date 2016/5/9 0009
 * @description
 */
public class AdminHandler extends Handler {


    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        next.handle(target, request, response, isHandled);
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
