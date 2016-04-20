package org.yoqu.cms.core.config;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import org.yoqu.cms.core.intercepter.AuthManager;
import org.yoqu.cms.core.model._MappingKit;

public class AppConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        PropKit.use("database_config.txt");
        // set app dev Mode debug
        me.setDevMode(PropKit.getBoolean("devMode", false));

    }

    @Override
    public void configRoute(Routes me) {
        me.add(new AdminRoutes("admin"));
        //admin..
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = createC3p0Plugin();
        me.add(c3p0Plugin);
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        me.add(activeRecordPlugin);
        _MappingKit.mapping(activeRecordPlugin);

    }

    @Override
    public void configInterceptor(Interceptors me) {
        AuthManager authManager = new AuthManager();
        me.add(authManager);
    }

    @Override
    public void configHandler(Handlers me) {

    }

    //create C3p0Plugin .
    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

}
