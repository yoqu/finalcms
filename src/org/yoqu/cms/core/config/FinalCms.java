package org.yoqu.cms.core.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.handler.Handler;
import org.yoqu.cms.core.util.AdminHandler;

import java.util.List;

/**
 * Created by yoqu on 16/8/29.
 */
public class FinalCms {

    private List<Handler> handlers;

    private List<Interceptor> interceptors;

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }
    private static FinalCms finalCms;

    public static FinalCms getInstance(){
        if (finalCms!=null){
            return finalCms;
        }else{
            return new FinalCms();
        }
    }

    /**
     * 执行构造方法时候默认初始化AdminHandler
     */
    public FinalCms(){
        initHandlers();
    }
    //初始化handler
    public void initHandlers(){
        Handler handler=new AdminHandler();
        handlers.add(handler);
    }
    public void initInterceptor(){
        AuthManagerInterceptor authManager = new AuthManagerInterceptor();
        this.interceptors.add(authManager);
    }

    public List<Handler> getHandlers() {
        return handlers;
    }


}
