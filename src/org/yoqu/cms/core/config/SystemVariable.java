package org.yoqu.cms.core.config;

import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.plugin.redis.Cache;
import org.yoqu.cms.core.model.Dictionary;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yoqu on 2016/4/19 0019.
 */
public class SystemVariable {

    public static final String MODULE="systemVariable";
    public static final String KEY="adminVariables";

    public void init() {

    }

    public static String get(String key) {
        HashMap<String, String> dictionaries=getMap();
        return dictionaries.get(key);
    }

    public static HashMap<String, String>  getMap(){
        return CacheKit.get(MODULE, KEY, new IDataLoader() {
            @Override
            public Object load() {
                HashMap<String, String> systemConstant = new HashMap<String, String>();
                for (Dictionary dictionary : Dictionary.dao.getDictionaryList()) {
                    systemConstant.put(dictionary.getName(), dictionary.getValue());
                }
                return systemConstant;
            }
        });
    }

    /**
     * 清除系统变量缓存...
     */
    public static void clearCache(){
        CacheKit.remove(MODULE,KEY);
    }

}

