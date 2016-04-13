package org.yoqu.cms.intercepter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.yoqu.cms.Constant.Constant;

/**
 * Created by yoqu on 2016/4/4 0004.
 */
public class AdminLoginIntercepter implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        String user = (String) invocation.getController().getSession().getAttribute(Constant.USER);
        if(user!=null){
            invocation.invoke();
        }
        else{
            invocation.getController().redirect("/admin/login.html");
        }

    }
}
