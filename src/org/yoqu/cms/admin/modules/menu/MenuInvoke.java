package org.yoqu.cms.admin.modules.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.yoqu.cms.core.aop.InvokeBefore;
import org.yoqu.cms.core.model.Menu;
import org.yoqu.cms.core.util.FinalProxy;

/**
 * Created by yoqu on 16-5-27.
 */
public class MenuInvoke {
    public static final String HOOK_SORT_MENU_ITEM_BEFORE = "SortMenuItem_Before";
    public static final String HOOK_SAVE_MENU_ITEM_BEFORE = "SaveMenuItem_Before";
    public static final String HOOK_UPDATE_MENU_ITEM_BEFORE = "UpdateMenuItem_Before";
    private volatile static MenuInvoke menuHook;

    public static MenuInvoke getInstance() {
        if (menuHook == null) {
            synchronized (MenuInvoke.class) {
                menuHook = (MenuInvoke) new FinalProxy().createProxy(MenuInvoke.class);
            }
        }
        return menuHook;
    }

    @InvokeBefore(HOOK_SORT_MENU_ITEM_BEFORE)
    public void sortMenuItem(JSONArray arrays, int fid) throws JSONException {
        Menu.dao.updateMenu(arrays, fid);
    }

    @InvokeBefore(HOOK_SAVE_MENU_ITEM_BEFORE)
    public boolean saveMenuItem(Menu menu) {
        return menu.save();
    }

    @InvokeBefore(HOOK_UPDATE_MENU_ITEM_BEFORE)
    public boolean updateMenuItem(Menu menu) {
        return menu.update();
    }
}
