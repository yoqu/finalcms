package org.yoqu.cms.core;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.yoqu.cms.Constant.Constant;
import org.yoqu.cms.config.AdminRoutes;

import javax.servlet.http.HttpSession;

/**
 * Created by yoqu on 2016/4/19 0019.
 */
public class AuthManager implements Interceptor {

    public static boolean webServiceAuth(Controller controller) {
        if (controller.getSession().getAttribute(Constant.ONLINE_USER) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void intercept(Invocation inv) {
        String uri = inv.getController().getRequest().getRequestURI();
        if (uri.startsWith("/admin")) {//compare user access back-end page or front page.
            if (webServiceAuth(inv.getController())) {
                inv.invoke();
            } else {
                inv.getController().redirect("/admin/user/login");
            }
        } else {
            inv.invoke();
        }
    }
}
