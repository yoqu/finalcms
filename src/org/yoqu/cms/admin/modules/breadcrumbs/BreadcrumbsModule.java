package org.yoqu.cms.admin.modules.breadcrumbs;

import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import org.yoqu.cms.core.aop.Hook;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.config.Module;
import org.yoqu.cms.core.model.Menu;

import java.util.List;

/**
 * Created by yoqu on 16-6-12.
 */
@Hook
public class BreadcrumbsModule extends Module {

    public static final String VARIABLE = "breadcrumbs";

    public static final Integer MENU_TYPE_LEFT = 5;

    /**
     * 通过Hook注入每个页面的面包屑
     * @see  org.yoqu.cms.admin.config.InjectManager
     * @param controller
     */
    public void breadcrumbsModule_AdminPageInject_Before(FinalBaseController controller) {
        String uri = controller.getRequest().getRequestURI();
        List<Menu> menus = CacheKit.get(MODULE, VARIABLE, new IDataLoader() {
            @Override
            public Object load() {
                return Menu.dao.sortMenu(Menu.dao.findAllMenuByType(MENU_TYPE_LEFT));
            }
        });
        controller.setAttr(VARIABLE, menus);
    }
}
