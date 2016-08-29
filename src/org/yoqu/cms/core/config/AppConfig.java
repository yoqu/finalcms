package org.yoqu.cms.core.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.config.*;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import org.yoqu.cms.admin.config.AdminRoutes;
import org.yoqu.cms.core.model._MappingKit;
import org.yoqu.cms.core.util.AdminHandler;
import org.yoqu.cms.front.config.FrontRoutes;
import org.yoqu.cms.plugin.SocketPlugin;

import java.util.List;

public class AppConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        PropKit.use("database_config.txt");
        // set app dev Mode debug
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setError404View("/admin/404.html");
    }

    @Override
    public void configRoute(Routes me) {
        me.add(new AdminRoutes("admin"));
        me.add(new FrontRoutes());
        //me.add("/admin/login", UserController.class);
        //admin..
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = createC3p0Plugin();
        me.add(c3p0Plugin);
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        me.add(activeRecordPlugin);
//        SocketPlugin socketPlugin=new SocketPlugin();
//        me.add(socketPlugin);
        _MappingKit.mapping(activeRecordPlugin);
        String path=this.getClass().getClassLoader().getResource("/").getPath();
        me.add(new EhCachePlugin(path+"ehcache.xml"));
    }

    @Override
    public void configInterceptor(Interceptors me) {
        List<Interceptor> interceptors=FinalCms.getInstance().getInterceptors();
        for (Interceptor interceptor:
             interceptors) {
            me.add(interceptor);
        }
    }

    @Override
    public void configHandler(Handlers me) {
        List<Handler> handlers=FinalCms.getInstance().getHandlers();
        for (Handler handler: handlers
             ) {
            me.add(handler);
        }
    }

    //create C3p0Plugin .
    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

}
