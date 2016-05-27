package org.yoqu.cms.core.config;

import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.redis.Cache;

/**
 * Created by yoqu on 16-5-27.
 */
public abstract class Module {
    public static final String MODULE="module";
    /**
     * 清除指定模块缓存
     */
    public static void clearCache(Object key){
        CacheKit.remove(MODULE,key);
    }

    /**
     * 清除所有模块的缓存.
     */
    public static void clearCache(){
        CacheKit.removeAll(MODULE);
    }
}
