package org.yoqu.cms.admin.modules.menu;

import com.jfinal.kit.LogKit;
import org.yoqu.cms.core.aop.Hook;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.model.Menu;

import java.util.List;

/**
 * Created by yoqu on 16-5-26.
 */
@Hook
public class MenuModule {

    /**
     * 注入菜单
     * @param controller
     */
    public void menuModuleAdminPageInject_Before(FinalBaseController controller){
        //左侧菜单.
        List<Menu> menus = Menu.dao.findAllMenuByType(4);
        controller.setAttr("site_left_menus",Menu.dao.sortMenu(menus));
    }
}
