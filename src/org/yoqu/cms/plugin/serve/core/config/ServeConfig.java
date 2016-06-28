package org.yoqu.cms.plugin.serve.core.config;

/**
 * @author yoqu
 * @date 2016/6/28 0028
 * @description
 */
public abstract class ServeConfig {

    public abstract void configRoute(Routes routes);

    public abstract void configConstant(Constant constant);

    public abstract Routes getRoutes();

    public abstract Constant getConstant();
}
