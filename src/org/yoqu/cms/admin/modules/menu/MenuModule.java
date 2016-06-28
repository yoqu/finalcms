package org.yoqu.cms.admin.modules.menu;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import org.json.JSONArray;
import org.yoqu.cms.core.aop.Hook;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.config.Module;
import org.yoqu.cms.core.model.Menu;

import java.util.List;

/**
 * Created by yoqu on 16-5-26.
 */
@Hook
public class MenuModule extends Module {
    public static final String VARIABLE = "site_left_menus";

    public static final Integer MENU_TYPE_LEFT = 4;

    /**
     * 通过Hook注入菜单
     * @see org.yoqu.cms.admin.config.InjectManager
     * @param controller
     */
    public void menuModule_AdminPageInject_Before(FinalBaseController controller) {
        //左侧菜单.
        List<Menu> menus = CacheKit.get(MODULE, VARIABLE, new IDataLoader() {
            @Override
            public Object load() {
                return Menu.dao.sortMenu(Menu.dao.findAllMenuByType(MENU_TYPE_LEFT));
            }
        });
        controller.setAttr(VARIABLE, menus);
    }


    public void menuModule_SortMenuItem_Before(JSONArray arrays, Integer fid) {
        clearCache(VARIABLE);
    }


    public void menuModule_SaveMenuItem_Before(Menu menu) {
        clearCache(VARIABLE);
    }

    public void menuModule_UpdateMenuItem_Before(Menu menu) {
        clearCache(VARIABLE);
    }

}
