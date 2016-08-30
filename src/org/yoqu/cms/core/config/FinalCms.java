package org.yoqu.cms.core.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.handler.Handler;
import org.yoqu.cms.core.module.ModuleHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoqu on 16/8/29.
 */
public class FinalCms {

    private List<Handler> handlers=new ArrayList<>();

    private List<Interceptor> interceptors=new ArrayList<>();

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }
    public Interceptor[] getInterceptortoArray(){
        return interceptors.toArray(new Interceptor[interceptors.size()]);
    }
    private static volatile FinalCms finalCms;

    public static FinalCms getInstance(){
        synchronized (FinalCms.class){
            if (finalCms!=null){
                return finalCms;
            }else{
                finalCms=new FinalCms();
                return finalCms;
            }
        }
    }

    /**
     * 执行构造方法时候默认初始化AdminHandler
     */
    public FinalCms(){
        initHandlers();
        initInterceptor();
    }
    //初始化handler
    public void initHandlers(){
        Handler handler=new ModuleHandler();
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
