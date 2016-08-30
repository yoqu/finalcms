package org.yoqu.cms.core.module;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.Action;
import org.yoqu.cms.core.config.FinalCms;
import org.yoqu.cms.core.model.Url;
import org.yoqu.cms.core.util.FileNameMatcher;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yoqu on 16/8/30.
 */
public class ModuleActionMapping {
    private List<Url> urls;
    public ModuleActionMapping(){
        buildMapping();
    }
    public void buildMapping() {
        urls = Url.dao.find("select * from url where is_delete=0");
    }

    public Url get(String target) {
        for (Url url : urls) {
            if (FileNameMatcher.match(target,url.getUrl())){
                return url;
            }
        }
        return null;
    }

    public Action getAction(String target){
        Url url=get(target);
        if (url!=null){
            try {
                Class actionClass=Class.forName(url.getController());
                Method method=actionClass.getMethod(url.getMethod());
                Interceptor[] interceptors= FinalCms.getInstance().getInterceptortoArray();
                Action action=new Action(null,target,actionClass,method,"",interceptors,url.getView());
                return action;
            } catch (ClassNotFoundException e) {
                return null;
            } catch (NoSuchMethodException e) {
                return null;
            }
        }else{
            return null;
        }
    }
}
