package org.yoqu.blog.config;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import org.yoqu.blog.controller.HelloController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
//import org.yoqu.blog.model._MappingKit;

public class AppConfig extends JFinalConfig
{
    @Override
    public void configConstant(Constants me)
    {
        // TODO Auto-generated method stub
        PropKit.use("DatabaseConfig.txt");
        // set app dev Mode debug
        me.setDevMode(PropKit.getBoolean("devMode",false));
    }

    @Override
    public void configRoute(Routes me)
    {
        // TODO Auto-generated method stub
        me.add("/hello", HelloController.class);
    }

    @Override
    public void configPlugin(Plugins me)
    {
        // TODO Auto-generated method stub
        C3p0Plugin c3p0Plugin =createC3p0Plugin();
        me.add(c3p0Plugin);
        ActiveRecordPlugin activeRecordPlugin =new ActiveRecordPlugin(c3p0Plugin);
        me.add(activeRecordPlugin);
       // _MappingKit.mapping(activeRecordPlugin);
    }

    @Override
    public void configInterceptor(Interceptors me)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void configHandler(Handlers me)
    {
        // TODO Auto-generated method stub
        
    }

    //create C3p0Plugin .
    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

}
